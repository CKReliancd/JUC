package common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ybs.web.dataserver.entity.sys.Menu;

public class MenuXmlLoad {

	private static String pid;

	public static List<Menu> getMenu() {

		String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		File f = new File(classPath + "Menu.xml");
		Map<String, Menu> map = new HashMap<>();
		List<Menu> menuList = new ArrayList<Menu>();
		// 创建DocumentBuilderFactory对象
		DocumentBuilderFactory a = DocumentBuilderFactory.newInstance();
		try {
			// 创建DocumentBuilder对象
			DocumentBuilder b = a.newDocumentBuilder();
			// 通过DocumentBuilder对象的parse方法返回一个Document对象
			Document document = b.parse(f);
			// 通过Document对象的getElementsByTagName()返根节点的一个list集合
			NodeList menuNodeList = document.getElementsByTagName("Menu");
			for (int i = 0; i < menuNodeList.getLength(); i++) {
				// 循环遍历获取每一个book
				Node book = menuNodeList.item(i);
				// 通过Node对象的getAttributes()方法获取全的属性值
				NamedNodeMap bookmap = book.getAttributes();
				// 循环遍每一个book的属性值
				Menu menu = new Menu();
				for (int j = 0; j < bookmap.getLength(); j++) {
					Node node = bookmap.item(j);
					// 通过Node对象的getNodeName()和getNodeValue()方法获取属性名和属性值

					if ("id".equals(node.getNodeName())) {
						menu.setId(node.getFirstChild().getNodeValue());
					}
					if ("name".equals(node.getNodeName())) {
						menu.setMenuName(node.getFirstChild().getNodeValue());
					}
					if ("hidden".equals(node.getNodeName())) {
						menu.setIsHidden(node.getFirstChild().getNodeValue());
					}
					if ("type".equals(node.getNodeName())) {
						menu.setMenuType(node.getFirstChild().getNodeValue());
					}
					if ("url".equals(node.getNodeName())) {
					/*	String url = node.getFirstChild().getNodeValue();
						if (!("".equals(url) || url == null)) {
						}*/
						menu.setAccessURL(node.getFirstChild().getNodeValue());
					}
				}
				// 父级id为 长度-2
				String id = menu.getId();
				if (id.length() <= 2) {
					menu.setPid("0");
				} else {
					menu.setPid(id.substring(0, id.length() - 2));
				}
				// 获取所有父级id
				getPid(id, "");
				String pidtemp = "";
				if ((!"".equals(pid)) && !(pid == null)) {
					String[] pids = pid.split(",");
					for (int k = pids.length - 1; k > 0; k--) {
						pidtemp = pidtemp + "," + pids[k];
					}
				}
				if (pidtemp == null || "".equals(pidtemp)) {
					pidtemp = ",";
				}
				menu.setPids(0 + pidtemp);
				// map.put(menu.getId(), menu);
				menuList.add(menu);
				// 解析book节点的子节点
				/*
				 * NodeList childlist = book.getChildNodes(); for(int t = 0;
				 * t<childlist.getLength(); t++){
				 * //区分出text类型的node以及element类型的node
				 * if(childlist.item(t).getNodeType() == Node.ELEMENT_NODE){
				 * System.out.print(childlist.item(t).getNodeName()+":");
				 * System.out.println(document.getElementsByTagName("url").item(
				 * i).getFirstChild().getNodeValue()); } }
				 */

			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return map;
		return menuList;
	}

	private static void getPid(String id, String pidTemp) {
		if (id.length() >= 2) {
			pid = pidTemp + id + ",";
			id = id.substring(0, id.length() - 2);
			getPid(id, pid);
		}

	}
	public static void main(String[] args) {
		List<Menu> list= getMenu();
		System.out.println(list.size());
	}
}
