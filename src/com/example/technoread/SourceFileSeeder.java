package com.example.technoread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class SourceFileSeeder {
	public static boolean SeedSourceStorage(Context context)
	{
		Delete(context);
		if(IsSeedRequired(context))
		{
	        	try
	        	{
	        		InputStream stream = GetAssetsInputStream(context);
	        		SeedDb(stream,context.openFileOutput(Constants.SOURCEFILE_NAME,Context.MODE_PRIVATE));
	        		return true;
				}
	        	catch (FileNotFoundException e)
	        	{
	        		return false;
				}
	        	catch (IOException e)
	        	{
	        		return false;
				}
		}
		return true;
	}
	
	public static boolean IsSeedRequired(Context context){	
		String appDbPath = GetSourceFilePath(context);
		File appdbFile = new File(appDbPath);
		if(!appdbFile.exists())
			return true;
		return false;
	}

	public static String GetSourceFilePath(Context context)
	{
		File file = context.getFilesDir();
		String filestr =  file.toString();
		return filestr + "/" + Constants.SOURCEFILE_NAME;
	}
	
	private static InputStream GetAssetsInputStream(Context context)
	{
		// db file is pacakged as part of the apk in assets folder.
		// read the db file as an input stream
		InputStream dbStream = null;
		try {
			AssetManager mgr = context.getAssets();
			dbStream = mgr.open(Constants.ASSETFILE_NAME + ".xml");
			return dbStream;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//UserMessage.ShowMessage(context, e.getMessage());
			return null;
		}
	}
	
	private static void SeedDb(InputStream open, FileOutputStream fileOutputStream) throws IOException
    {
    	byte[] buffer = new byte[1024];
    	int length = 0;
		while((length = open.read(buffer)) > 0)
		{
			fileOutputStream.write(buffer,0,length);
		}
		open.close();
		fileOutputStream.close();	
	}
	
	private static void Delete(Context context)
	{
		String appDbPath = GetSourceFilePath(context);
		File appdbFile = new File(appDbPath);
		if(appdbFile.exists())
		{
			context.deleteFile(Constants.SOURCEFILE_NAME);
		}
	}
	
	
	public static boolean BackupAppDb(Context context) throws IOException
	{
		String dbDestinationPath = GetDestinationFilePath(context);
		File dbOutputPathFile = new File(dbDestinationPath);
		if(dbOutputPathFile.exists())
		{
			// delete the file first
			dbOutputPathFile.delete();
		}
			FileInputStream inputStream = null;
			try {
				String sourcePath = GetSourceFilePath(context);
				inputStream = new FileInputStream(sourcePath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileOutputStream outputStream = new FileOutputStream(dbOutputPathFile);
			SeedDb(inputStream, outputStream);
			
			return true;
	}
	
	private static String GetDestinationFilePath(Context context)
	{
		File path =  context.getExternalFilesDir(null);
		String dbPath = path.getAbsolutePath() + "//" + "SourceDb.xml";
		
		///storage/sdcard0/Android/data/com.reversecurrent.ehr/files
		return dbPath;
	}
}
