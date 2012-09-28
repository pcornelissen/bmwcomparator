package de.orchit.bmwcomparator;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainServlet extends HttpServlet {

	public static final Pattern PATTERN = Pattern.compile(".*seite=([0-9]+).*");

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final TreeMap<String, String> alle = new TreeMap<String, String>();
		final ArrayList<CarData> autos = new ArrayList<CarData>();
		String url = request.getParameter("url");
		final TagNode tagNode = urlToNode(url);

		try {
			int pageCount = processPage(request, tagNode, alle, autos);
			if (pageCount > 1) {
				final int startIndex;
				if (url.contains("seite=")) {
					Matcher m = PATTERN.matcher(url);

					if (m.find()) {
						startIndex = Integer.valueOf(m.group(1)) + 1;
					} else startIndex = 2;

				} else startIndex = 2;
				url=url.replaceAll("&seite=([0-9]+)","");
				pageCount = Math.min(startIndex + 2, pageCount);
				for (int page = startIndex; pageCount >= page; page++) {
					final TagNode tagNode2 = urlToNode(url + "&seite=" + page);
					processPage(request, tagNode2, alle, autos);
				}
			}
		} catch (XPatherException e) {
			throw new ServletException(e);
		}

		request.setAttribute("hasData", autos.size() > 0);
		request.setAttribute("alle", alle);
		request.setAttribute("autos", autos);
		request.setAttribute("url", url);
		getServletContext().getRequestDispatcher("/WEB-INF/result.jsp").forward
				(request, response);
	}

	private int processPage(HttpServletRequest request, TagNode tagNode, SortedMap<String, String> alle, List<CarData> autos) throws XPatherException, IOException {
		final Object[] objects = tagNode.evaluateXPath("//h5[@class='titel']/a/@href");

		for (Object itemUrlObj : objects) {
			String itemUrl = "http://www.carpresenter.de" + itemUrlObj;
			TagNode tagNode2 = urlToNode(itemUrl);
			CarData carData = new CarData();
			carData.setLink(itemUrl);
			carData.setName(extractSingleValueByXPath(tagNode2, "//title/text()"));
			carData.setImage(extractSingleValueByXPath(tagNode2, "//div[@id='detailbild-container-1']/a/img/@src"));
			carData.setPrice(extractSingleValueByXPath(tagNode2, "//tr[td/strong='Angebotspreis']/td[2]/text()"));
			carData.setSoldOn(extractSingleValueByXPath(tagNode2, "//tr[td/text()='Erstzulassung']/td[2]/text()"));
			carData.setColor(extractSingleValueByXPath(tagNode2, "//tr[td/text()='Farbe']/td[2]/text()"));
			carData.setKm(extractSingleValueByXPath(tagNode2, "//tr[td/text()='KM-Stand']/td[2]/text()"));

			extractOptions(tagNode2, carData);
			autos.add(carData);
			alle.putAll(carData.getOptions());
		}
		String lastPageUrl = extractSingleValueByXPath(tagNode, "//a[@class='blaettern-letzte']/@href");
		if (lastPageUrl == null) return 0;
		lastPageUrl = lastPageUrl.substring(lastPageUrl.lastIndexOf("=") + 1);
		return Integer.valueOf(lastPageUrl);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("url") != null) {
			doPost(request, response);
			return;
		}
		request.setAttribute("url", request.getParameter("url"));
		request.setAttribute("hasData", false);
		getServletContext().getRequestDispatcher("/WEB-INF/result.jsp").forward
				(request, response);
	}

	private TagNode urlToNode(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		URLConnection uCon = url.openConnection();
		InputStream is = uCon.getInputStream();
		return new HtmlCleaner().clean(is, "iso-8859-1");
	}

	private void extractOptions(TagNode tagNode2, CarData carData) throws XPatherException {
		extractOptionsFromData(tagNode2, carData, "//div[@id='pager-inhalt-sonder']/text()", "X");
		extractOptionsFromData(tagNode2, carData, "//div[@id='pager-inhalt-serie']/text()", "S");
	}

	private void extractOptionsFromData(TagNode tagNode2, CarData carData, String xPathExpression, String marker) throws XPatherException {
		final String optionsString = extractSingleValueByXPath(tagNode2, xPathExpression);
		String[] options = optionsString.split(",");
		Map<String, String> optionMap = new HashMap<String, String>();
		for (String element : options) {
			element = element.trim();

			if (element.startsWith("Ehemalige")) break;
			optionMap.put(element, marker);
		}
		carData.getOptions().putAll(optionMap);
	}

	private String extractSingleValueByXPath(TagNode tagNode2, String xPathExpression) throws XPatherException {
		final Object[] objects = tagNode2.evaluateXPath(xPathExpression);
		if (objects.length > 0) return objects[0].toString();
		return null;
	}


}
