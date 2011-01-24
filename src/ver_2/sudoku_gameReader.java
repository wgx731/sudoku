package ver_2;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class sudoku_gameReader {

	static void Readxml(File file, Frame game) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath = factory.newXPath();
		try {

			String expression = null;

			InputSource inputSource;

			for (int r = 0; r < 9; r++) {
				inputSource = new InputSource(new FileInputStream(file));
				expression = "//puzzle/R" + (r + 1);
				XPathExpression xPathExpression = xPath.compile(expression);
				NodeList nodes = (NodeList) xPathExpression.evaluate(
						inputSource, XPathConstants.NODESET);

				Node item = nodes.item(0);
				String[] row = item.getTextContent().split(" ");
				for (int c = 0; c < 9; c++) {
					game.puzzle[r][c] = Integer.valueOf(row[c]);
				}
			}
			for (int r = 0; r < 9; r++) {
				inputSource = new InputSource(new FileInputStream(file));
				expression = "//answer/R" + (r + 1);
				XPathExpression xPathExpression = xPath.compile(expression);
				NodeList nodes = (NodeList) xPathExpression.evaluate(
						inputSource, XPathConstants.NODESET);

				Node item = nodes.item(0);
				String[] row = item.getTextContent().split(" ");
				for (int c = 0; c < 9; c++) {
					game.puzzle[r][c] = Integer.valueOf(row[c]);
				}
			}
			inputSource = new InputSource(new FileInputStream(file));
			expression = "//REDO";
			XPathExpression xPathExpression = xPath.compile(expression);
			NodeList nodes = (NodeList) xPathExpression.evaluate(inputSource,
					XPathConstants.NODESET);
			Node item = nodes.item(0);
			String[] redo = item.getTextContent().split(":");
			game.redo.clear();
			if (redo.length > 1){
				//System.out.println(redo.length);
				for (int i = 1; i < redo.length - 2; i += 2) {
					int r = -1, c = -1, v = -1;
					r = Integer.valueOf(redo[i].substring(0,1));
					c = Integer.valueOf(redo[i+1].substring(0,1));
					v = Integer.valueOf(redo[i+2].substring(0,1));
					if (r != -1 && c != -1 && v != -1) {
						game.redo.add(new Redo(new Point(r, c), v));
					}
				}
			}
			inputSource = new InputSource(new FileInputStream(file));
			expression = "//UNDO";
			xPathExpression = xPath.compile(expression);
			nodes = (NodeList) xPathExpression.evaluate(inputSource,
					XPathConstants.NODESET);
			item = nodes.item(0);
			String[] undo = item.getTextContent().split(":");
			game.filledup.clear();
			if (undo.length > 1){
				for (int i = 1; i <undo.length - 2; i+=2) {
					int r = -1, c = -1, v = -1;
					r = Integer.valueOf(undo[i].substring(0,1));
					c = Integer.valueOf(undo[i+1].substring(0,1));
					v = Integer.valueOf(undo[i+2].substring(0,1));
					if (r != -1 && c != -1 && v != -1) {
						//System.out.println(r+" "+c+" "+v);
						game.filledup.add(new Point(r, c));
						game.puzzle[r][c] = v;
					}
				}
			}
			game.gamestarted = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
