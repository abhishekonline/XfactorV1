package org.twinone.locker.pickup.pattern;
/**
 * Created by julie on 7/29/15.
 */

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OnePersonData {
	List<Feature> data;
	String parentPath;
	String user;
	String label;

	private String TAG = "======";

	
	public boolean readFromFolder(String path, String label) {
		this.label = label;
		File parent = new File(path);

		File[] files = parent.listFiles();
		Log.d(TAG,"Path:" + path + " has " + files.length + " files");
		for (int i = 0; i < files.length; i++) {

			String name = files[i].getName();
			if (files[i].isFile() && getSubfix(name).equals("csv")
					&& getType(name).equals("acc")) {
				Log.d(TAG,"readFromFolder: got file:" + name);
				String prefix = this.getPrefix(name);
				Log.d(TAG,"Prefix:" + prefix);
				Feature feature = new Feature();
				feature.getFeatureFromCSV(path, prefix);
				if (data == null) {
					data = new ArrayList<Feature>();
				}
				data.add(feature);
			}
		}
		return true;
	}

	
	public void setUser(String user) {
		this.user = user;
	}
	
	private String getPrefix(String filename) {
		int lastIndex = filename.lastIndexOf('_');
		return filename.substring(0, lastIndex + 1);
	}

	private String getSubfix(String filename) {
		int index = filename.indexOf('.');
		return filename.substring(index + 1, filename.length());
	}

	private String getType(String filename) {
		int dotpos = filename.indexOf('.');
		int last = filename.lastIndexOf('_');
		return filename.substring(last + 1, dotpos);
	}

	public void write2File(String filename,boolean withHeader) {
		String NEW_LINE_SEPARATOR = "\n";
		FileWriter fileWriter = null;
		
		try {
			fileWriter = new FileWriter(filename,true);
			if (withHeader) {
				fileWriter.append("@relation pickup");
				fileWriter.append(NEW_LINE_SEPARATOR);
				fileWriter.append(NEW_LINE_SEPARATOR);
				String[] attributes = data.get(0).getHeader().split(",");
				for (int i = 0; i < attributes.length - 1; i++) {
					fileWriter.append("@attribute ");
					fileWriter.append(attributes[i] + " ");
					fileWriter.append("real");
					fileWriter.append(NEW_LINE_SEPARATOR);
				}
				fileWriter.append("@attribute ");
				fileWriter.append("label {0,1}");
				fileWriter.append(NEW_LINE_SEPARATOR);
				
				fileWriter.append(NEW_LINE_SEPARATOR);
				fileWriter.append("@data ");
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			for (int i = 0; i < data.size(); i++) {
				fileWriter.append(data.get(i).toString() + this.label);
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out
						.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		String readpath;
		String tofile;
		tofile = "/Users/julie/pickupData/training.arff";
		
		readpath = "/Users/julie/pickupData/edu.cmu.ebiz.pickup_zhoumi2/files/";
		OnePersonData onepersondata = new OnePersonData();
		onepersondata.setUser("Zhoumi");
		onepersondata.readFromFolder(readpath, "1");
		onepersondata.write2File(tofile,true);
		
		
		// Jhalak
		readpath = "/Users/julie/pickupData/edu.cmu.ebiz.pickup_Jhalak/files/";
		onepersondata = new OnePersonData();
		onepersondata.setUser("Jhalak");
		onepersondata.readFromFolder(readpath, "0"); //label = 0
		onepersondata.write2File(tofile,false); //append
		
		//Linxue
		readpath = "/Users/julie/pickupData/edu.cmu.ebiz.pickup_linxue/files/";
		onepersondata = new OnePersonData();
		onepersondata.setUser("LinXue");
		onepersondata.readFromFolder(readpath, "0"); //label = 0
		onepersondata.write2File(tofile,false); //append
		
		//Miranda
		readpath = "/Users/julie/pickupData/edu.cmu.ebiz.pickup_Miranda/files/";
		onepersondata = new OnePersonData();
		onepersondata.setUser("Miranda");
		onepersondata.readFromFolder(readpath, "0"); //label = 0
		onepersondata.write2File(tofile,false); //append
		
		//Thomas
		readpath = "/Users/julie/pickupData/edu.cmu.ebiz.pickup_Thomas/files/";
		onepersondata = new OnePersonData();
		onepersondata.setUser("Thomas");
		onepersondata.readFromFolder(readpath, "0"); //label = 0
		onepersondata.write2File(tofile,false); //append
		
		
//		//Zengjiabei
		readpath = "/Users/julie/pickupData/edu.cmu.ebiz.pickup_zhengjiabei/files/";
		onepersondata = new OnePersonData();
		onepersondata.setUser("Jiabei");
		onepersondata.readFromFolder(readpath, "0"); //label = 0
		onepersondata.write2File(tofile,false); //append
	}
}
