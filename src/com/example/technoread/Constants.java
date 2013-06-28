package com.example.technoread;

public class Constants {
	public static final String SOURCEFILE_NAME = "Sources.xml";
	public final static String ASSETFILE_NAME = "source";
	public static final String ITEMS_XPATH = "/Sources/Source";
	// Never put fully qualified path for eg /Sources/Source/Title. Instead only use direct node name i.e Title.
	// If you dond do this, XPath::Evaluate will always start from start of document instead of from that node.
	public static final String ITEMTITLE_XPATH = "Title";
	public static final String ITEMURL_XPATH = "Url";
	public static final String ITEMDESCRIPTION_XPATH = "Description";
	public static final String ITEMOPENINLINE_XPATH = "OpenInline";
	public static final String MODELKEY = "newssourcekey";
	public static final String POSITIONKEY = "positionkey";
}
