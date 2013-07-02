package com.example.technoread;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

public class DALWriter {
	public static void SaveToFile(Context context,ArrayList<NewsSourceModel> modelList) throws IllegalArgumentException, IllegalStateException, IOException
	{
		XmlSerializer xs = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		xs.setOutput(writer);
		xs.startDocument("UTF-8", true);
		xs.startTag("", "Sources");
		for(NewsSourceModel model: modelList)
		{
			xs.startTag("", "Source");
			SetTag(xs, model.Title,"Title");
			SetTag(xs, model.Description,"Description");
			SetTag(xs, model.Url,"Url");
			SetTag(xs, String.valueOf(model.OpenInline),"OpenInline");
			SetTag(xs, String.valueOf(model.IsAdded),"IsAdded");
			xs.endTag("", "Source");
		}
		xs.endTag("", "Sources");
		xs.endDocument();
		String content = writer.toString();
		String filePath = SourceFileSeeder.GetSourceFilePath(context);
		FileWriter fileWriter = new FileWriter(filePath, false);
		fileWriter.write(content);
		fileWriter.close();
	}

	private static void SetTag(XmlSerializer xs, String value,String tagName)
			throws IOException {
		
		xs.startTag("", tagName);
		xs.text(value);
		xs.endTag("",tagName);
	}
}
