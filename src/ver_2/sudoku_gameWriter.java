package ver_2;

import java.awt.Point;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class sudoku_gameWriter {
	
	static void writeXML(String file,Frame game){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element root = document.createElement("gameboard");
			root.setAttribute("row", "9");
			root.setAttribute("col", "9");
			document.appendChild(root);
			Element puzzle = document.createElement("puzzle");
			for (int r = 1; r <=9; r++){
				Element temp = document.createElement("R"+r);
				String content = "";
				for (int c = 0; c<9; c++){
					content += game.puzzle[r-1][c]+" ";
				}
				temp.setTextContent(content);
				puzzle.appendChild(temp);
			}
			Element answer = document.createElement("answer");
			for (int r = 1; r <=9; r++){
				Element temp = document.createElement("R"+r);
				String content = "";
				for (int c = 0; c<9; c++){
					content += game.puzzle[r-1][c]+" ";
				}
				temp.setTextContent(content);
				answer.appendChild(temp);
			}
			root.appendChild(puzzle);
			root.appendChild(answer);
			String undocontent = "";
			for (Point p: game.filledup){
				int r = p.x;
				int c = p.y;
				undocontent += "R:"+r+"C:"+c+"N:"+game.puzzle[r][c]+" ";
			}
			Element undo = document.createElement("UNDO");
			undo.setTextContent(undocontent);
			root.appendChild(undo);
			String redocontent = "";
			for (Redo d: game.redo){
				int r = d.getP().x;
				int c = d.getP().y;
				redocontent += "R:"+r+"C:"+c+"N:"+d.getValue()+" ";
			}
			Element redo = document.createElement("REDO");
			redo.setTextContent(redocontent);
			root.appendChild(redo);
			saveXML(document, file);
			game.gamestarted = false;

		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		}
	}
	
	
	
	private static boolean saveXML(Document document, String path) {
		TransformerFactory factory = TransformerFactory.newInstance();
		boolean result = true;

		try {
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "4");

			Source src = new DOMSource(document);
			Result dest = new StreamResult(new File(path));
			transformer.transform(src, dest);
		} catch (TransformerConfigurationException e) {
			result = false;
		} catch (TransformerException e) {
			result = false;
		}

		return result;
	}
	
}
