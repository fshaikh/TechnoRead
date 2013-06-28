package com.example.technoread;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;


public class DOMFeedParser
{
	private Document _rssDocument = null;
	private Element _rssDocumentElement = null;
	private Context _context = null;
	
	public DOMFeedParser(Context context)
	{
		_context = context;
	}
	

	public void Close() {
		_rssDocument = null;
		_rssDocumentElement = null;
	}
	

	public boolean LoadParser()
	{
		try
		{
			// First validate the xml
			if(!Validate())
				return false;
		}
		catch(Exception exObj)
		{
			return false;
		}
		return true;
	}
	public ArrayList<NewsSourceModel> GetItems() {
		return 	PopulateSourceItems();
	}

	private boolean Validate() throws Exception
	{
		try
		{
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			if(!ValidateCore(docBuilder))
				return false;
			// see if its a valid rss
			_rssDocumentElement = _rssDocument.getDocumentElement();
			if(_rssDocumentElement == null)
				return false;
			return true;
		}
		catch(Exception exObj)
		{
			return false;
		}
	}
	
	private boolean ValidateCore(DocumentBuilder builder)
	{
		FileInputStream inputStream = null;
			try
			{
				inputStream = _context.openFileInput(Constants.SOURCEFILE_NAME);
				_rssDocument =  builder.parse(inputStream);
			}
			catch (SAXException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			finally
			{
				if(inputStream != null)
				{
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					inputStream = null;
				}
			}
			return true;
	}

	private ArrayList<NewsSourceModel> PopulateSourceItems() {
		XPath xPath = GetXPath();
		ArrayList<NewsSourceModel> list = new ArrayList<NewsSourceModel>();
		try {
			NodeList itemsNodeList = (NodeList)xPath.evaluate(Constants.ITEMS_XPATH, _rssDocumentElement, XPathConstants.NODESET);
			if(itemsNodeList != null)
			{
				for(int i=0;i<itemsNodeList.getLength();i++)
				{
					Node itemNode = itemsNodeList.item(i);			
					CreateItem(itemNode,list);
					
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	private void CreateItem(Node itemNode,ArrayList<NewsSourceModel> Items) {
		XPath xPath = GetXPath();
		NewsSourceModel sourceItem = new NewsSourceModel();
		try {
			// TITLE
			Node titleNode = (Node)xPath.evaluate(Constants.ITEMTITLE_XPATH, itemNode,XPathConstants.NODE);
			if(titleNode != null)
				sourceItem.Title = titleNode.getTextContent();
			
			// URL
			Node urlNode = (Node)xPath.evaluate(Constants.ITEMURL_XPATH, itemNode,XPathConstants.NODE);
			if(urlNode != null)
				sourceItem.Url = urlNode.getTextContent();
			
			// DESCRIPTION
			Node descriptionNode = (Node)xPath.evaluate(Constants.ITEMDESCRIPTION_XPATH, itemNode,XPathConstants.NODE);
			if(descriptionNode != null)
				sourceItem.Description = descriptionNode.getTextContent();
			
			// Open inline
			Node openInlineNode = (Node)xPath.evaluate(Constants.ITEMOPENINLINE_XPATH, itemNode,XPathConstants.NODE);
			if(openInlineNode != null)
				sourceItem.OpenInline = Boolean.parseBoolean(openInlineNode.getTextContent());
			
				
			Items.add(sourceItem);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private XPath GetXPath()
	{
		return XPathFactory.newInstance().newXPath();
	}
}
