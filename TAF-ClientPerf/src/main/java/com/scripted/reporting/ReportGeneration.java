package com.scripted.reporting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.scripted.api.PerfAPIClient;

public class ReportGeneration {
	static String reportFilePath;
	public static String cdir = System.getProperty("user.dir");
	public static String currentTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
			.format(Calendar.getInstance().getTime());
	public static String username = System.getProperty("user.name");
	static StringBuffer listWebBuffer = new StringBuffer();
	static StringBuffer listMobBuffer = new StringBuffer();
	static StringBuffer modelBuffer = new StringBuffer();
	static StringBuffer chartBuffer = new StringBuffer();
	static StringBuffer overAllWebBuffer = new StringBuffer();
	static StringBuffer overAllMobileBuffer = new StringBuffer();
	static StringBuffer overAllGraphBuffer = new StringBuffer();
	static Double overAllWebScore = null;
	static Double overAllMobileScore = null;

	public static void reportWriter() {
		PrintWriter pw;
		reportFilePath = cdir + "/SkriptmateReport/ClientSidePerformance" + File.separator + currentTimeStamp;
		com.scripted.generic.FileUtils.makeDirs(reportFilePath);
		File destinationFolder = new File(reportFilePath);
		File sourceFolder = new File(cdir + "/src/main/resources/ClientSidePerformance/Artefacts");
		try {
			copyFolder(sourceFolder, destinationFolder);
			System.out.println("Started to create report");

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh.mm.ss aa");
			String dateString = dateFormat.format(new Date()).toString();
			pw = new PrintWriter(new FileWriter(reportFilePath + File.separator + "ClientSidePerf_Report.html"));

			overAllScoreWebBuffer();
			overAllScoreMobileBuffer();
			addDesktopModelBuffer();
			addMobileModelBuffer();
			addListWebBuffer();
			addListMobBuffer();
			addOverAllGraphBuffer();
			pw.println("<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "\n" + "<head>\n" + "  <meta charset=\"utf-8\">\n"
					+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
					+ "  <title>Client Side Performace Report</title>\n"
					+ "  <link rel=\"stylesheet\" href=\"css/bootstrap.min.css\">\n"
					+ "  <link rel=\"stylesheet\" type=\"text/css\" href=\"css/jquery.dataTables.min.css\">\n"
					+ "  <link rel=\"stylesheet\" type=\"text/css\" href=\"css/CSP_style.css\">\n"
					+ "  <script src=\"js/jquery.min.js\"></script>\n"
					+ "  <script src=\"js/jquery-3.3.1.min.js\"></script>\n"
					+ "  <script src=\"js/bootstrap.min.js\"></script>\n"
					+ "  <script src=\"js/jquery.dataTables.min.js\"></script>\n" + "\n" + "  <script>\n" + "    \n"
					+ "    $(document).ready(function(){\n" + "      $(\"#listmob\").hide();\n"
					+ "    $('#technology').on('change', function() {\n" + "      \n"
					+ "      if ( this.value == 'web')\n" + "      //.....................^.......\n" + "      {\n"
					+ "        $(\"#listweb\").show();\n" + "        $(\"#listmob\").hide();\n" + "      }\n"
					+ "      else\n" + "      {\n" + "      \n" + "        $(\"#listmob\").show();\n"
					+ "        $(\"#listweb\").hide();\n" + "      }\n" + "\n" + "  \n" + "    });\n" + "});\n"
					+ "    </script>\n" + "\n" + "</head>");

			pw.println("<body>\n" + "  <div class=\"container\">\n" + "    <div class=\"header\">\n"
					+ "      <div class=\"logo_sec\">\n"
					+ "        <img src=\"img/sklogo.png\" class=\"logo-size\" alt=\"logo-skrptmate\"/>\n"
					+ "      </div>\n" + "      <div class=\"text_sec\">\n"
					+ "        <div class=\"heading1\">Client Side Performace Report</div>\n"
					+ "        <div class=\"heading2\"><span>Executed By:</span><span>" + username + "</span></div>\n"
					+ "        <div class=\"heading2\"><span>Execution Time:</span><span>" + dateString
					+ "</span></div>\n" + "      </div>\n" + "    </div>\n" + "\n" + "    <div class=\"section_1\">\n"
					+ "      <label style=\"font-weight:normal; margin-right: 10px; font-size: 16px; color:#2a3f54;\">Overall Performance :</label>\n"
					+ "      <select  id=\"technology\" class=\"dropstyle\">\n"
					+ "        <option value=\"web\">Web</option>\n"
					+ "        <option value=\"mobile\">Mobile</option>\n" + "      </select>\n"
					+ "      <hr style=\"margin:3px 0px 3px 0px\">\n" + "    </div>");
			pw.println(overAllWebBuffer);
			pw.println(listWebBuffer);
			pw.println(overAllMobileBuffer);
			pw.println(listMobBuffer);
			pw.println("</div>");
			pw.println(modelBuffer);
			pw.println(" <div class=\"modal fade\" id=\"myModal\" role=\"dialog\">\n"
					+ "        <div class=\"modal-dialog\" style=\"width:fit-content;\">\n" + "        \n"
					+ "          <!-- Modal content-->\n" + "          <div class=\"modal-content\">\n"
					+ "            <div class=\"modal-header\">\n"
					+ "              <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\n"
					+ "              <h5 class=\"modal-title\" id=\"myHeader\">Screenshot</h5>\n"
					+ "            </div>\n" + "            <div class=\"modal-body\">\n"
					+ "			<p><img id=\"myImg\" alt=\"Screenshot_icon\" style=\"width:500px;height:348px;border: 1px solid #ddd;border-radius: 4px;padding: 5px;\"/></p>\n"
					+ "            </div>\n" + "            \n" + "          </div>\n" + "          \n"
					+ "        </div>\n" + "      </div>\n"
					+ "   <div class=\"modal fade\" id=\"mobScrnShotModal\" role=\"dialog\" >\n"
					+ "        <div class=\"modal-dialog\" style=\"width:fit-content;\" >\n" + "       \n"
					+ "          <!-- Modal content-->\n" + "          <div class=\"modal-content\">\n"
					+ "            <div class=\"modal-header\">\n"
					+ "              <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\n"
					+ "              <h5 class=\"modal-title\" id=\"myHeader1\">Screenshot</h5>\n"
					+ "            </div>\n" + "            <div class=\"modal-body\">\n"
					+ "			<p><img id=\"myImg1\" alt=\"Screenshot_icon\" style=\"width:280px;height:498px;border: 1px solid #ddd;border-radius: 4px;padding: 5px;\"/></p>\n"
					+ "            </div>\n" + "            \n" + "          </div>\n" + "          \n"
					+ "        </div>\n" + "      </div>\n" + "\n" + "\n" + "\n" + "\n" + "<script>\n"
					+ "	function displayImage(imgsrc,text)\n" + "	{\n"
					+ "		document.getElementById('myHeader').innerText = text;\n"
					+ "		document.getElementById('myImg').src = imgsrc;\n" + "	}</script><script>\n"
					+ "	function displayImage1(imgsrc, text) {\n"
					+ "		document.getElementById('myHeader1').innerText = text;\n"
					+ "		document.getElementById('myImg1').src = imgsrc;\n" + "	}\n" + "</script> \n"
					+ "	</body>");

			pw.println("<script src=\"js/Chart.js\"></script>\r\n"
					+ "<script src=\"js/pluginsDatalabels\"></script><script src=\"https://bernii.github.io/gauge.js/dist/gauge.min.js\"></script>");
			pw.println(overAllGraphBuffer);
			pw.println(chartBuffer);
			pw.println("</html>");
			pw.close();
			System.out.println("Client side performance report created successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void overAllScoreWebBuffer() {
		String reportName = PerfAPIClient.getReportName();
		List<String> desktopPagesList = new ArrayList<>();
		File file = new File(reportName);
		try {
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject obj = new JSONObject(content);
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject deskJsonObj = (JSONObject) obj;
			JSONObject deskLightJsonObj = (JSONObject) obj;
			JSONObject deskLightCatgryJsonObj = (JSONObject) obj;
			JSONObject deskLightPerfJsonObj = (JSONObject) obj;
			Double scoreVal = null;

			List<String> pageNames = PerfAPIClient.getPageNamesList();
			for (int i = 0; i < pageNames.size(); i++) {
				if (pageNames.get(i).contains(": Desktop"))
					desktopPagesList.add(pageNames.get(i));
			}
			double allScoreVal = 0;
			for (int j = 0; j < desktopPagesList.size(); j++) {
				deskJsonObj = (JSONObject) jsonObject.get(desktopPagesList.get(j));
				deskLightJsonObj = (JSONObject) deskJsonObj.get("lighthouseResult");
				deskLightCatgryJsonObj = (JSONObject) deskLightJsonObj.get("categories");
				deskLightPerfJsonObj = (JSONObject) deskLightCatgryJsonObj.get("performance");
				String score = deskLightPerfJsonObj.get("score").toString();
				scoreVal = (double) Math.round(Double.parseDouble(score) * 100);
				allScoreVal = allScoreVal + scoreVal;
			}
			overAllWebScore = allScoreVal / desktopPagesList.size();
			overAllWebBuffer.append("<div id=\"listweb\" class=\"section_2\"> \n"
					+ "    <div class=\"row\" style=\"margin-bottom:20px;\"> \n" + "         \n"
					+ "          <div class=\"col-md-4 col-lg-4\" style=\"margin-top:20px;\">\n" + "         \n"
					+ "          <div class=\"indicators\" style=\"border-left:5px solid #FF4E42; color:#FF4E42\">\n"
					+ "            <span>0-49</span>\n" + "            <span style=\"margin-left: 5px;\">Slow</span>\n"
					+ "          </div>\n"
					+ "          <div class=\"indicators\" style=\"border-left:5px solid #FFA400; color:#FFA400\">\n"
					+ "            <span>50-89</span>\n"
					+ "            <span style=\"margin-left: 5px;\">Moderate</span>\n" + "          </div>\n"
					+ "          <div class=\"indicators\" style=\"border-left:5px solid #0CCE6B; color: #0CCE6B;\">\n"
					+ "            <span>90-100</span>\n"
					+ "            <span style=\"margin-left: 5px;\">Fast</span>\n" + "          </div>\n" + "\n"
					+ "          </div>\n" + "      \n" + "          <div class=\"col-md-4 col-lg-4 graph_wrap\">\n"
					+ "                \n" + "            <div>\n" + "              \n"
					+ "                <canvas id=\"myChart\"></canvas>\n" + "                \n"
					+ "            </div>\n" + "          </div> \n" + "\n"
					+ "          <div class=\"col-md-4 col-lg-4 score_wrap\">      \n"
					+ "            <div class=\"scorelabel\">Score</div>\n" + "            <div class=\"score\">"
					+ overAllWebScore + "</div>\n" + "          </div> \n" + " \n" + "    </div>\n" + " \n" + "\n"
					+ "\n" + "    \n" + "\n" + "\n" + "\n"
					+ "        <div style=\"color: #2a3f54; font-size: 16px;\">Pages</div>\n"
					+ "        <hr style=\"margin:6px 0px 20px 0px\">");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void overAllScoreMobileBuffer() {
		String reportName = PerfAPIClient.getReportName();
		List<String> mobilePagesList = new ArrayList<>();
		File file = new File(reportName);
		try {
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject obj = new JSONObject(content);
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject deskJsonObj = (JSONObject) obj;
			JSONObject deskLightJsonObj = (JSONObject) obj;
			JSONObject deskLightCatgryJsonObj = (JSONObject) obj;
			JSONObject deskLightPerfJsonObj = (JSONObject) obj;
			Double scoreVal = null;

			List<String> pageNames = PerfAPIClient.getPageNamesList();
			for (int i = 0; i < pageNames.size(); i++) {
				if (pageNames.get(i).contains(": Mobile"))
					mobilePagesList.add(pageNames.get(i));
			}
			double allScoreVal = 0;
			for (int j = 0; j < mobilePagesList.size(); j++) {
				deskJsonObj = (JSONObject) jsonObject.get(mobilePagesList.get(j));
				deskLightJsonObj = (JSONObject) deskJsonObj.get("lighthouseResult");
				deskLightCatgryJsonObj = (JSONObject) deskLightJsonObj.get("categories");
				deskLightPerfJsonObj = (JSONObject) deskLightCatgryJsonObj.get("performance");
				String score = deskLightPerfJsonObj.get("score").toString();
				scoreVal = (double) Math.round(Double.parseDouble(score) * 100);
				allScoreVal = allScoreVal + scoreVal;
			}
			overAllMobileScore = allScoreVal / mobilePagesList.size();
			overAllMobileBuffer.append("<div id=\"listmob\" class=\"section_2\" > \n" + "\n"
					+ "                <div class=\"row\" style=\"margin-bottom:20px;\"> \n"
					+ "                      \n"
					+ "                  <div class=\"col-md-4 col-lg-4\" style=\"margin-top:20px;\">\n"
					+ "                \n"
					+ "                  <div class=\"indicators\" style=\"border-left:5px solid #FF4E42; color:#FF4E42\">\n"
					+ "                    <span>0-49</span>\n"
					+ "                    <span style=\"margin-left: 5px;\">Slow</span>\n"
					+ "                  </div>\n"
					+ "                  <div class=\"indicators\" style=\"border-left:5px solid #FFA400; color:#FFA400\">\n"
					+ "                    <span>50-89</span>\n"
					+ "                    <span style=\"margin-left: 5px;\">Moderate</span>\n"
					+ "                  </div>\n"
					+ "                  <div class=\"indicators\" style=\"border-left:5px solid #0CCE6B; color: #0CCE6B;\">\n"
					+ "                    <span>90-100</span>\n"
					+ "                    <span style=\"margin-left: 5px;\">Fast</span>\n"
					+ "                  </div>\n" + "\n" + "                  </div>\n" + "\n"
					+ "                  <div class=\"col-md-4 col-lg-4 graph_wrap\">\n" + "                        \n"
					+ "                    <div>\n" + "                      \n"
					+ "                        <canvas id=\"myChart2\"></canvas>\n" + "                        \n"
					+ "                    </div>\n" + "                  </div> \n" + "\n"
					+ "                  <div class=\"col-md-4 col-lg-4 score_wrap\">      \n"
					+ "                    <div class=\"scorelabel\">Score</div>\n"
					+ "                    <div class=\"score2\">" + overAllMobileScore + "</div>\n"
					+ "                  </div> \n" + "\n" + "              </div>\n"
					+ "              <div style=\"color: #2a3f54; font-size: 16px;\">Pages</div>\n"
					+ "        <hr style=\"margin:6px 0px 20px 0px\">");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addListWebBuffer() {
		String reportName = PerfAPIClient.getReportName();
		List<String> desktopPagesList = new ArrayList<>();
		File file = new File(reportName);
		try {
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject obj = new JSONObject(content);
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject deskJsonObj = (JSONObject) obj;
			JSONObject deskLightJsonObj = (JSONObject) obj;
			JSONObject deskLightCatgryJsonObj = (JSONObject) obj;
			JSONObject deskLightPerfJsonObj = (JSONObject) obj;
			Double scoreVal = null;

			List<String> pageNames = PerfAPIClient.getPageNamesList();
			for (int i = 0; i < pageNames.size(); i++) {
				if (pageNames.get(i).contains(": Desktop"))
					desktopPagesList.add(pageNames.get(i));
			}
			for (int j = 0; j < desktopPagesList.size(); j++) {
				deskJsonObj = (JSONObject) jsonObject.get(desktopPagesList.get(j));
				deskLightJsonObj = (JSONObject) deskJsonObj.get("lighthouseResult");
				deskLightCatgryJsonObj = (JSONObject) deskLightJsonObj.get("categories");
				deskLightPerfJsonObj = (JSONObject) deskLightCatgryJsonObj.get("performance");
				String score = deskLightPerfJsonObj.get("score").toString();
				scoreVal = (double) Math.round(Double.parseDouble(score) * 100);

				if (scoreVal.longValue() >= 50 && scoreVal.longValue() <= 89) {
					listWebBuffer.append(" <div class=\"band2 list\">\n" + "          <div class=\"row\">\n"
							+ "                <div class=\"col-md-3\" style=\"color:#2a3f54;\">"
							+ desktopPagesList.get(j).replace(": Desktop", "").trim() + "</div>\n"
							+ "                <div class=\"col-md-2\" style=\"text-align: right;\"><label style=\"font-weight:normal; font-size: 14px;color:#2a3f54; \">Overall Performance</label></div>  \n"
							+ "                <div class=\"col-md-4\">\n"
							+ "                <div class=\"progress custom_bar\">\n"
							+ "                  <div class=\"progress-bar color2\" role=\"progressbar\" aria-valuenow=\"70\"aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:"
							+ scoreVal.longValue() + "%\"></div>\n" + "                </div>\n"
							+ "                </div> \n"
							+ "                <div class=\"col-md-1\" style=\"padding: 0px;\"><label style=\"font-size: 14px; color:#FFA400;\">"
							+ scoreVal.longValue() + "%</label></div>\n"
							+ "                <div class=\"col-md-2\"><button type=\"button\" class=\"btn btn-success cust_btn\" data-toggle=\"modal\" data-target=\"#DeskModel"
							+ j
							+ "\">Details</button><img align=\"right\" src=\"img/screenshot.png\" class=\"icon_table\" data-toggle=\"modal\" data-target=\"#myModal\" alt=\"Screenshot_icon\" onclick='displayImage(\"screenshots/"
							+desktopPagesList.get(j).replace(": Desktop", "").trim() + "_Desktop.png\",\""
							+ desktopPagesList.get(j).replace(": Desktop", "").trim() + "\");'></div>\n"
							+ "          </div>\n" + "        </div>");

				} else if (scoreVal.longValue() >= 90 && scoreVal.longValue() <= 100) {
					listWebBuffer.append(" <div class=\"band1 list\">\n" + "          <div class=\"row\">\n"
							+ "                <div class=\"col-md-3\" style=\"color:#2a3f54;\">"
							+ desktopPagesList.get(j).replace(": Desktop", "").trim() + "</div>\n"
							+ "                <div class=\"col-md-2\" style=\"text-align: right;\"><label style=\"font-weight:normal; font-size: 14px;color:#2a3f54; \">Overall Performance</label></div>  \n"
							+ "                <div class=\"col-md-4\">\n"
							+ "                <div class=\"progress custom_bar\">\n"
							+ "                  <div class=\"progress-bar color1\" role=\"progressbar\" aria-valuenow=\"70\"aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:"
							+ scoreVal.longValue() + "%\"></div>\n" + "                </div>\n"
							+ "                </div> \n"
							+ "                <div class=\"col-md-1\" style=\"padding: 0px;\"><label style=\"font-size: 14px; color:#0CCE6B;\">"
							+ scoreVal.longValue() + "%</label></div>\n"
							+ "                <div class=\"col-md-2\"><button type=\"button\" class=\"btn btn-success cust_btn\" data-toggle=\"modal\" data-target=\"#DeskModel"
							+ j
							+ "\">Details</button><img align=\"right\" src=\"img/screenshot.png\" class=\"icon_table\" data-toggle=\"modal\" data-target=\"#myModal\" alt=\"Screenshot_icon\" onclick='displayImage(\"screenshots/"
							+ desktopPagesList.get(j).replace(": Desktop", "").trim() + "_Desktop.png\",\""
							+ desktopPagesList.get(j).replace(": Desktop", "").trim() + "\");'></div>\n"
							+ "          </div>\n" + "        </div>");

				} else {
					listWebBuffer.append(" <div class=\"band3 list\">\n" + "          <div class=\"row\">\n"
							+ "                <div class=\"col-md-3\" style=\"color:#2a3f54;\">"
							+ desktopPagesList.get(j).replace(": Desktop", "").trim() + "</div>\n"
							+ "                <div class=\"col-md-2\" style=\"text-align: right;\"><label style=\"font-weight:normal; font-size: 14px;color:#2a3f54; \">Overall Performance</label></div>  \n"
							+ "                <div class=\"col-md-4\">\n"
							+ "                <div class=\"progress custom_bar\">\n"
							+ "                  <div class=\"progress-bar color3\" role=\"progressbar\" aria-valuenow=\"70\"aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:"
							+ scoreVal.longValue() + "%\"></div>\n" + "                </div>\n"
							+ "                </div> \n"
							+ "                <div class=\"col-md-1\" style=\"padding: 0px;\"><label style=\"font-size: 14px; color:#FF4E42;\">"
							+ scoreVal.longValue() + "%</label></div>\n"
							+ "                <div class=\"col-md-2\"><button type=\"button\" class=\"btn btn-success cust_btn\" data-toggle=\"modal\" data-target=\"#DeskModel"
							+ j
							+ "\">Details</button><img align=\"right\" src=\"img/screenshot.png\" class=\"icon_table\" data-toggle=\"modal\" data-target=\"#myModal\" alt=\"Screenshot_icon\" onclick='displayImage(\"screenshots/"
							+ desktopPagesList.get(j).replace(": Desktop", "").trim()+ "_Desktop.png\",\""
							+ desktopPagesList.get(j).replace(": Desktop", "").trim() + "\");'></div>\n"
							+ "          </div>\n" + "        </div>");

				}

			}

			listWebBuffer.append("</div>");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void addListMobBuffer() {
		String reportName = PerfAPIClient.getReportName();
		List<String> mobilePagesList = new ArrayList<>();
		File file = new File(reportName);
		try {
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject obj = new JSONObject(content);
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject deskJsonObj = (JSONObject) obj;
			JSONObject deskLightJsonObj = (JSONObject) obj;
			JSONObject deskLightCatgryJsonObj = (JSONObject) obj;
			JSONObject deskLightPerfJsonObj = (JSONObject) obj;
			Double scoreVal = null;

			List<String> pageNames = PerfAPIClient.getPageNamesList();
			for (int i = 0; i < pageNames.size(); i++) {
				if (pageNames.get(i).contains(": Mobile"))
					mobilePagesList.add(pageNames.get(i));
			}
			for (int j = 0; j < mobilePagesList.size(); j++) {
				deskJsonObj = (JSONObject) jsonObject.get(mobilePagesList.get(j));
				deskLightJsonObj = (JSONObject) deskJsonObj.get("lighthouseResult");
				deskLightCatgryJsonObj = (JSONObject) deskLightJsonObj.get("categories");
				deskLightPerfJsonObj = (JSONObject) deskLightCatgryJsonObj.get("performance");
				String score = deskLightPerfJsonObj.get("score").toString();
				scoreVal = (double) Math.round(Double.parseDouble(score) * 100);

				if (scoreVal.longValue() >= 50 && scoreVal.longValue() <= 89) {
					listMobBuffer.append(" <div class=\"band2 list\">\n" + "          <div class=\"row\">\n"
							+ "                <div class=\"col-md-3\" style=\"color:#2a3f54;\">"
							+ mobilePagesList.get(j).replace(": Mobile", "").trim() + "</div>\n"
							+ "                <div class=\"col-md-2\" style=\"text-align: right;\"><label style=\"font-weight:normal; font-size: 14px;color:#2a3f54; \">Overall Performance</label></div>  \n"
							+ "                <div class=\"col-md-4\">\n"
							+ "                <div class=\"progress custom_bar\">\n"
							+ "                  <div class=\"progress-bar color2\" role=\"progressbar\" aria-valuenow=\"70\"aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:"
							+ scoreVal.longValue() + "%\"></div>\n" + "                </div>\n"
							+ "                </div> \n"
							+ "                <div class=\"col-md-1\" style=\"padding: 0px;\"><label style=\"font-size: 14px; color:#FFA400;\">"
							+ scoreVal.longValue() + "%</label></div>\n"
							+ "                <div class=\"col-md-2\"><button type=\"button\" class=\"btn btn-success cust_btn\" data-toggle=\"modal\" data-target=\"#MobModel"
							+ j
							+ "\">Details</button><img align=\"right\" src=\"img/screenshot.png\" class=\"icon_table\" data-toggle=\"modal\" data-target=\"#mobScrnShotModal\" alt=\"Screenshot_icon\" onclick='displayImage1(\"screenshots/"
							+  mobilePagesList.get(j).replace(": Mobile", "").trim()+ "_Mobile.png\",\""
							+ mobilePagesList.get(j).replace(": Mobile", "").trim() + "\");'></div>\n"
							+ "          </div>\n" + "        </div>");

				} else if (scoreVal.longValue() >= 90 && scoreVal.longValue() <= 100) {
					listMobBuffer.append(" <div class=\"band1 list\">\n" + "          <div class=\"row\">\n"
							+ "                <div class=\"col-md-3\" style=\"color:#2a3f54;\">"
							+ mobilePagesList.get(j).replace(": Mobile", "").trim() + "</div>\n"
							+ "                <div class=\"col-md-2\" style=\"text-align: right;\"><label style=\"font-weight:normal; font-size: 14px;color:#2a3f54; \">Overall Performance</label></div>  \n"
							+ "                <div class=\"col-md-4\">\n"
							+ "                <div class=\"progress custom_bar\">\n"
							+ "                  <div class=\"progress-bar color1\" role=\"progressbar\" aria-valuenow=\"70\"aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:"
							+ scoreVal.longValue() + "%\"></div>\n" + "                </div>\n"
							+ "                </div> \n"
							+ "                <div class=\"col-md-1\" style=\"padding: 0px;\"><label style=\"font-size: 14px; color:#0CCE6B;\">"
							+ scoreVal.longValue() + "%</label></div>\n"
							+ "                <div class=\"col-md-2\"><button type=\"button\" class=\"btn btn-success cust_btn\" data-toggle=\"modal\" data-target=\"#MobModel"
							+ j
							+ "\">Details</button><img align=\"right\" src=\"img/screenshot.png\" class=\"icon_table\" data-toggle=\"modal\" data-target=\"#mobScrnShotModal\" alt=\"Screenshot_icon\" onclick='displayImage1(\"screenshots/"
							+  mobilePagesList.get(j).replace(": Mobile", "").trim() + "_Mobile.png\",\""
							+ mobilePagesList.get(j).replace(": Mobile", "").trim() + "\");'></div>\n"
							+ "          </div>\n" + "        </div>");
				} else {
					listMobBuffer.append(" <div class=\"band3 list\">\n" + "          <div class=\"row\">\n"
							+ "                <div class=\"col-md-3\" style=\"color:#2a3f54;\">"
							+ mobilePagesList.get(j).replace(": Mobile", "").trim() + "</div>\n"
							+ "                <div class=\"col-md-2\" style=\"text-align: right;\"><label style=\"font-weight:normal; font-size: 14px;color:#2a3f54; \">Overall Performance</label></div>  \n"
							+ "                <div class=\"col-md-4\">\n"
							+ "                <div class=\"progress custom_bar\">\n"
							+ "                  <div class=\"progress-bar color3\" role=\"progressbar\" aria-valuenow=\"70\"aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width:"
							+ scoreVal.longValue() + "%\"></div>\n" + "                </div>\n"
							+ "                </div> \n"
							+ "                <div class=\"col-md-1\" style=\"padding: 0px;\"><label style=\"font-size: 14px; color:#FF4E42;\">"
							+ scoreVal.longValue() + "%</label></div>\n"
							+ "                <div class=\"col-md-2\"><button type=\"button\" class=\"btn btn-success cust_btn\" data-toggle=\"modal\" data-target=\"#MobModel"
							+ j
							+ "\">Details</button><img align=\"right\" src=\"img/screenshot.png\" class=\"icon_table\" data-toggle=\"modal\" data-target=\"#mobScrnShotModal\" alt=\"Screenshot_icon\" onclick='displayImage1(\"screenshots/"
							+  mobilePagesList.get(j).replace(": Mobile", "").trim() + "_Mobile.png\",\""
							+ mobilePagesList.get(j).replace(": Mobile", "").trim() + "\");'></div>\n"
							+ "          </div>\n" + "        </div>");
				}

			}

			listMobBuffer.append("</div>");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void addOverAllGraphBuffer() {

		overAllGraphBuffer.append("<script>\n" + "  demoGauge = new Gauge(document.getElementById(\"myChart\"));\n"
				+ "  var opts = {\n" + "      angle: .01,\n" + "      lineWidth: 0.20,\n" + "      radiusScale: 0.9,\n"
				+ "      pointer: {\n" + "          length: 0.5,\n" + "          strokeWidth: 0.05,\n"
				+ "          color: '#000000'\n" + "      },\n" + "      staticLabels: {\n"
				+ "          font: \"16px sans-serif\",\n" + "          \n"
				+ "          labels: [0,20, 40, 60, 80,100],\n" + "          fractionDigits: 0,\n" + "      },\n"
				+ "      staticZones: [\n" + "          { strokeStyle: \"#F03E3E\", min: 0, max: 49},\n"
				+ "          { strokeStyle: \"#FFA500\", min: 50, max: 89 },\n"
				+ "          { strokeStyle: \"#30B32D\", min: 90, max:100 },\n" + "  \n" + "      ],\n"
				+ "      limitMax: false,\n" + "      limitMin: false,\n" + "      highDpiSupport: true\n" + "  };\n"
				+ "  demoGauge.setOptions(opts);\n" + "  demoGauge.minValue = 0;\n" + "  demoGauge.maxValue = 100;\n"
				+ "  demoGauge.set(" + overAllWebScore + ");\n" + "</script>");
		overAllGraphBuffer.append("<script>\n" + "  demoGauge = new Gauge(document.getElementById(\"myChart2\"));\n"
				+ "  var opts = {\n" + "      angle: .01,\n" + "      lineWidth: 0.20,\n" + "      radiusScale: 0.9,\n"
				+ "      pointer: {\n" + "          length: 0.5,\n" + "          strokeWidth: 0.05,\n"
				+ "          color: '#000000'\n" + "      },\n" + "      staticLabels: {\n"
				+ "          font: \"16px sans-serif\",\n" + "          \n"
				+ "          labels: [0,20, 40, 60, 80,100],\n" + "          fractionDigits: 0,\n" + "      },\n"
				+ "      staticZones: [\n" + "          { strokeStyle: \"#F03E3E\", min: 0, max: 49},\n"
				+ "          { strokeStyle: \"#FFA500\", min: 50, max: 89 },\n"
				+ "          { strokeStyle: \"#30B32D\", min: 90, max:100 },\n" + "  \n" + "      ],\n"
				+ "      limitMax: false,\n" + "      limitMin: false,\n" + "      highDpiSupport: true\n" + "  };\n"
				+ "  demoGauge.setOptions(opts);\n" + "  demoGauge.minValue = 0;\n" + "  demoGauge.maxValue = 100;\n"
				+ "  demoGauge.set(" + overAllMobileScore +");\n" + "</script>");

	}

	public static void addDesktopModelBuffer() {
		String reportName = PerfAPIClient.getReportName();
		List<String> desktopPagesList = new ArrayList<>();
		File file = new File(reportName);
		try {
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject obj = new JSONObject(content);
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject deskLightJsonObj = (JSONObject) obj;
			JSONObject deskLightAuditsJsonObj = (JSONObject) obj;
			JSONObject deskAuditsFCPJsonObj = (JSONObject) obj;
			JSONObject deskAuditsLCPJsonObj = (JSONObject) obj;
			JSONObject deskAuditsTBTJsonObj = (JSONObject) obj;
			JSONObject deskAuditsSIJsonObj = (JSONObject) obj;
			JSONObject deskAuditsTTIJsonObj = (JSONObject) obj;
			JSONObject deskAuditsCLSJsonObj = (JSONObject) obj;
			JSONObject deskJsonObj = (JSONObject) obj;
			JSONObject deskLoadExpJsonObj = (JSONObject) obj;
			JSONObject deskLoadMetricesJsonObj = (JSONObject) obj;
			JSONObject deskFCPJsonObj = (JSONObject) obj;
			JSONObject deskLCPJsonObj = (JSONObject) obj;
			JSONObject deskFIDJsonObj = (JSONObject) obj;
			JSONObject deskCLSJsonObj = (JSONObject) obj;
			JSONObject deskScrnShotJsonObj = (JSONObject) obj;
			JSONObject deskScrnShotDetailJsonObj = (JSONObject) obj;

			List<String> pageNames = PerfAPIClient.getPageNamesList();

			for (int i = 0; i < pageNames.size(); i++) {
				if (pageNames.get(i).contains(": Desktop"))
					desktopPagesList.add(pageNames.get(i));
			}
			for (int j = 0; j < desktopPagesList.size(); j++) {
				deskJsonObj = (JSONObject) jsonObject.get(desktopPagesList.get(j));
				deskLoadExpJsonObj = (JSONObject) deskJsonObj.get("loadingExperience");
				deskLoadMetricesJsonObj = (JSONObject) deskLoadExpJsonObj.get("metrics");

				deskFCPJsonObj = (JSONObject) deskLoadMetricesJsonObj.get("FIRST_CONTENTFUL_PAINT_MS");
				String FCPscore = deskFCPJsonObj.get("percentile").toString();

				Double FCPscoreVal = Double.parseDouble(FCPscore);
				Double FCPPercentval = null;
				if (FCPscoreVal > 1000) {
					FCPscoreVal = FCPscoreVal / 1000;
					DecimalFormat df = new DecimalFormat("#.##");

					FCPscore = df.format(FCPscoreVal) + "sec";
				} else {
					DecimalFormat df = new DecimalFormat("#.##");

					FCPscore = df.format(FCPscoreVal) + "ms";
				}

				List<String> FCPScorePercent = new ArrayList<String>();
				JSONArray FCBJsonArray = deskFCPJsonObj.getJSONArray("distributions");
				for (int i = 0; i < FCBJsonArray.length(); i++) {
					JSONObject percentJobj = FCBJsonArray.getJSONObject(i);
					String FCPPercent = percentJobj.get("proportion").toString();
					FCPPercentval = (double) Math.round(Double.parseDouble(FCPPercent) * 100);
					long val = FCPPercentval.longValue();
					FCPScorePercent.add(Long.toString(val));

				}

				deskLCPJsonObj = (JSONObject) deskLoadMetricesJsonObj.get("LARGEST_CONTENTFUL_PAINT_MS");
				String LCPscore = deskLCPJsonObj.get("percentile").toString();

				Double LCPscoreVal = Double.parseDouble(LCPscore);
				Double LCPPercentval = null;
				if (LCPscoreVal > 1000) {
					LCPscoreVal = LCPscoreVal / 1000;
					DecimalFormat df = new DecimalFormat("#.##");

					LCPscore = df.format(LCPscoreVal) + "sec";
				} else {
					DecimalFormat df = new DecimalFormat("#.##");

					LCPscore = df.format(LCPscoreVal) + "ms";
				}

				List<String> LCPScorePercent = new ArrayList<String>();
				JSONArray LCBJsonArray = deskLCPJsonObj.getJSONArray("distributions");
				for (int i = 0; i < LCBJsonArray.length(); i++) {
					JSONObject percentJobj = LCBJsonArray.getJSONObject(i);
					String LCPPercent = percentJobj.get("proportion").toString();
					LCPPercentval = (double) Math.round(Double.parseDouble(LCPPercent) * 100);
					long val = LCPPercentval.longValue();
					LCPScorePercent.add(Long.toString(val));

				}

				deskFIDJsonObj = (JSONObject) deskLoadMetricesJsonObj.get("FIRST_INPUT_DELAY_MS");
				String FIDscore = deskFIDJsonObj.get("percentile").toString();

				Double FIDscoreVal = Double.parseDouble(FIDscore);
				Double FIDPercentval = null;
				if (FIDscoreVal > 1000) {
					FIDscoreVal = FIDscoreVal / 1000;
					DecimalFormat df = new DecimalFormat("#.##");

					FIDscore = df.format(FIDscoreVal) + "sec";
				} else {
					DecimalFormat df = new DecimalFormat("#.##");

					FIDscore = df.format(FIDscoreVal) + "ms";
				}

				List<String> FIDScorePercent = new ArrayList<String>();
				JSONArray FIDJsonArray = deskFIDJsonObj.getJSONArray("distributions");
				for (int i = 0; i < FIDJsonArray.length(); i++) {
					JSONObject percentJobj = FIDJsonArray.getJSONObject(i);
					String FIDPercent = percentJobj.get("proportion").toString();
					FIDPercentval = (double) Math.round(Double.parseDouble(FIDPercent) * 100);
					long val = FIDPercentval.longValue();
					FIDScorePercent.add(Long.toString(val));

				}

				deskCLSJsonObj = (JSONObject) deskLoadMetricesJsonObj.get("CUMULATIVE_LAYOUT_SHIFT_SCORE");
				String CLSscore = deskCLSJsonObj.get("percentile").toString();

				Double CLSscoreVal = Double.parseDouble(CLSscore);
				Double CLSPercentval = null;
				if (CLSscoreVal > 1000) {
					CLSscoreVal = CLSscoreVal / 1000;
					DecimalFormat df = new DecimalFormat("#.##");

					CLSscore = df.format(CLSscoreVal) + "sec";
				} else {
					DecimalFormat df = new DecimalFormat("#.##");

					CLSscore = df.format(CLSscoreVal) + "ms";
				}

				List<String> CLSScorePercent = new ArrayList<String>();
				JSONArray CLSJsonArray = deskCLSJsonObj.getJSONArray("distributions");
				for (int i = 0; i < CLSJsonArray.length(); i++) {
					JSONObject percentJobj = CLSJsonArray.getJSONObject(i);
					String CLSPercent = percentJobj.get("proportion").toString();
					CLSPercentval = (double) Math.round(Double.parseDouble(CLSPercent) * 100);
					long val = CLSPercentval.longValue();
					CLSScorePercent.add(Long.toString(val));

				}

				deskLightJsonObj = (JSONObject) deskJsonObj.get("lighthouseResult");
				deskLightAuditsJsonObj = (JSONObject) deskLightJsonObj.get("audits");

				deskAuditsFCPJsonObj = (JSONObject) deskLightAuditsJsonObj.get("first-contentful-paint");
				String FCPmode = deskAuditsFCPJsonObj.getString("scoreDisplayMode");
				String auditFCPScore = null;
				if (FCPmode.equalsIgnoreCase("numeric")) {
					auditFCPScore = deskAuditsFCPJsonObj.getString("displayValue");
				}

				deskAuditsLCPJsonObj = (JSONObject) deskLightAuditsJsonObj.get("largest-contentful-paint");
				String LCPmode = deskAuditsLCPJsonObj.getString("scoreDisplayMode");
				String auditLCPScore = null;
				if (LCPmode.equalsIgnoreCase("numeric")) {
					auditLCPScore = deskAuditsLCPJsonObj.getString("displayValue");
				}

				deskAuditsTBTJsonObj = (JSONObject) deskLightAuditsJsonObj.get("total-blocking-time");
				String TBTmode = deskAuditsTBTJsonObj.getString("scoreDisplayMode");
				String auditTBTScore = null;
				if (TBTmode.equalsIgnoreCase("numeric")) {
					auditTBTScore = deskAuditsTBTJsonObj.getString("displayValue");
				}

				deskAuditsSIJsonObj = (JSONObject) deskLightAuditsJsonObj.get("speed-index");
				String SImode = deskAuditsSIJsonObj.getString("scoreDisplayMode");
				String auditSIScore = null;
				if (SImode.equalsIgnoreCase("numeric")) {
					auditSIScore = deskAuditsSIJsonObj.getString("displayValue");
				}

				deskAuditsTTIJsonObj = (JSONObject) deskLightAuditsJsonObj.get("interactive");
				String TTImode = deskAuditsTTIJsonObj.getString("scoreDisplayMode");
				String auditTTIScore = null;
				if (TTImode.equalsIgnoreCase("numeric")) {
					auditTTIScore = deskAuditsTTIJsonObj.getString("displayValue");
				}

				deskAuditsCLSJsonObj = (JSONObject) deskLightAuditsJsonObj.get("cumulative-layout-shift");
				String CLSmode = deskAuditsCLSJsonObj.getString("scoreDisplayMode");
				String auditCLSScore = null;
				if (CLSmode.equalsIgnoreCase("numeric")) {
					auditCLSScore = deskAuditsCLSJsonObj.getString("displayValue");
				}

				deskScrnShotJsonObj = (JSONObject) deskLightAuditsJsonObj.get("final-screenshot");
				deskScrnShotDetailJsonObj = (JSONObject) deskScrnShotJsonObj.get("details");
				String scrnShotData = deskScrnShotDetailJsonObj.getString("data");
				String trimData = scrnShotData.replace("data:image/jpeg;base64,", "").trim();
				byte[] imageByteArray = Base64.decodeBase64(trimData);

				File scrnshotDir = new File(reportFilePath + "/screenshots");
				if (!scrnshotDir.exists())
					scrnshotDir.mkdir();
				File imgFile = new File(reportFilePath + "/screenshots/"
						+ desktopPagesList.get(j).replace(": Desktop", "").trim() + "_Desktop.png");
				OutputStream os = new FileOutputStream(imgFile);
				os.write(imageByteArray);

				modelBuffer.append(" <div class=\"modal fade\" id=" + "DeskModel" + j + " role=\"dialog\">\r\n"
						+ "        <div class=\"modal-dialog\">\r\n" + "        \r\n"
						+ "          <!-- Modal content-->\r\n" + "          <div class=\"modal-content\">\r\n"
						+ "            <div class=\"modal-header\">\r\n"
						+ "              <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\r\n"
						+ "              <h5 class=\"modal-title\">"
						+ desktopPagesList.get(j).replace(": Desktop", "").trim() + " </h5>\r\n"
						+ "            </div>\r\n"
						+ "            <div class=\"modal-body\" style=\"padding: 20px;\">\r\n"
						+ "              <div style=\"color: #2a3f54; font-size: 14px; font-weight: bold;\">Field Data</div>\r\n"
						+ "              <div class=\"row\" style=\"margin-top: 10px;\">\r\n"
						+ "                  <div class=\"col-md-3 col-lg-3\">\r\n"
						+ "                    <div class=\"chart_lbl\">First Contentful Paint(FCP)</div>\r\n"
						+ "                    <div>\r\n" + "                        <canvas id=" + "DeskChart" + j
						+ "0></canvas>\r\n" + "                    </div>\r\n");

				if (FCPscore.contains("ec")) {
					String FCBScoreTrim = FCPscore.replace("sec", "").trim();
					Double FCBScoreInt = Double.parseDouble(FCBScoreTrim);
					if (FCBScoreInt >= 0 && FCBScoreInt <= 2)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
					else if (FCBScoreInt >= 2 && FCBScoreInt <= 4)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FFA400;\">");
					else
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FF4E42;\">");
				} else {
					modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
				}
				modelBuffer.append(FCPscore + "</div>\r\n" + "                  </div> \r\n" + "                  \r\n"
						+ "                  <div class=\"col-md-3 col-lg-3\">\r\n"
						+ "                    <div class=\"chart_lbl\">Largest Contentful Paint(LCP)</div>\r\n"
						+ "                    <div>\r\n" + "                        <canvas id=" + "DeskChart" + j
						+ "1></canvas>\r\n" + "                    </div>\r\n");

				if (LCPscore.contains("ec")) {
					String LCBScoreTrim = LCPscore.replace("sec", "").trim();
					Double LCBScoreInt = Double.parseDouble(LCBScoreTrim);
					if (LCBScoreInt >= 0 && LCBScoreInt <= 2)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
					else if (LCBScoreInt >= 2 && LCBScoreInt <= 4)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FFA400;\">");
					else
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FF4E42;\">");
				} else {
					modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
				}
				modelBuffer.append(LCPscore + "</div>\r\n" + "                  </div> \r\n" + "\r\n"
						+ "                  <div class=\"col-md-3 col-lg-3\">\r\n"
						+ "                    <div class=\"chart_lbl\">First Input Delay(FID)</div>\r\n"
						+ "                    <div>\r\n" + "                        <canvas id=" + "DeskChart" + j
						+ "2></canvas>\r\n" + "                    </div>\r\n");
				if (FIDscore.contains("ec")) {
					String FIDScoreTrim = FIDscore.replace("sec", "").trim();
					Double FIDScoreInt = Double.parseDouble(FIDScoreTrim);
					if (FIDScoreInt >= 0 && FIDScoreInt <= 2)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
					else if (FIDScoreInt >= 2 && FIDScoreInt <= 4)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FFA400;\">");
					else
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FF4E42;\">");
				} else {
					modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
				}
				modelBuffer.append(FIDscore + "</div>\r\n" + "                  </div> \r\n" + "\r\n"
						+ "                  <div class=\"col-md-3 col-lg-3\">\r\n"
						+ "                    <div class=\"chart_lbl\">Cumulative Layout Shift(CLS)</div>\r\n"
						+ "                    <div>\r\n" + "                        <canvas id=" + "DeskChart" + j
						+ "3></canvas>\r\n" + "                    </div>\r\n");

				if (CLSscore.contains("ec")) {
					String CLSScoreTrim = CLSscore.replace("sec", "").trim();
					Double CLSScoreInt = Double.parseDouble(CLSScoreTrim);
					if (CLSScoreInt >= 0 && CLSScoreInt <= 2)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
					else if (CLSScoreInt >= 2 && CLSScoreInt <= 4)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FFA400;\">");
					else
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FF4E42;\">");
				} else {
					modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
				}
				modelBuffer.append(CLSscore + "</div>\r\n" + "                  </div> \r\n"
						+ "              </div>\r\n" + "\r\n"
						+ "              <div style=\"color: #2a3f54; font-size: 14px; font-weight: bold;\">Lab Data</div>\r\n"
						+ "\r\n");
				modelBuffer
						.append("  <div class=\"row\" style=\"margin-top: 10px;\"><div class=\"col-md-4 col-lg-4\">\r\n"
								+ "                    <table style=\"width:100%\">\r\n"
								+ "                      <tr>\r\n"
								+ "                        <td style=\"color: #2a3f54;\">First Contentful Paint(FCP)</td>\r\n"
								+ "                        <td style=\"color: #FF4E42; font-weight: bold;\">");

				modelBuffer.append(auditFCPScore + "</td>\r\n" + "                      </tr>\r\n"
						+ "                      <tr>\r\n"
						+ "                        <td style=\"color: #2a3f54;\">Speed Index</td>\r\n"
						+ "                        <td style=\"color: #FF4E42; font-weight: bold;\">" + auditSIScore
						+ "</td>\r\n" + "                      </tr>\r\n" + "                    </table>\r\n"
						+ "                  </div>\r\n" + "\r\n"
						+ "                  <div class=\"col-md-4 col-lg-4\">\r\n"
						+ "                    <table style=\"width:100%\">\r\n" + "                      <tr>\r\n"
						+ "                        <td style=\"color: #2a3f54;\">Largest Contentful Paint(LCP)</td>\r\n"
						+ "                        <td style=\"color: #FF4E42; font-weight: bold;\">" + auditLCPScore
						+ "</td>\r\n" + "                      </tr>\r\n" + "                      <tr>\r\n"
						+ "                        <td style=\"color: #2a3f54;\">Time to Interactive</td>\r\n"
						+ "                        <td style=\"color: #FF4E42; font-weight: bold;\">" + auditTTIScore
						+ "</td>\r\n" + "                      </tr>\r\n" + "                    </table>\r\n"
						+ "                  </div>\r\n" + "\r\n"
						+ "                  <div class=\"col-md-4 col-lg-4\">\r\n"
						+ "                    <table style=\"width:100%\">\r\n" + "                      <tr>\r\n"
						+ "                        <td style=\"color: #2a3f54;\">Total Blocking Time</td>\r\n"
						+ "                        <td style=\"color: #FF4E42; font-weight: bold;\">" + auditTBTScore
						+ "</td>\r\n" + "                      </tr>\r\n" + "                      <tr>\r\n"
						+ "                        <td style=\"color: #2a3f54;\">Cumulative Layout Shift(CLS)</td>\r\n"
						+ "                        <td style=\"color: #FF4E42; font-weight: bold;\">" + auditCLSScore
						+ "</td>\r\n" + "                      </tr>\r\n" + "                    </table>\r\n"
						+ "                  </div>\r\n" + "\r\n" + "                \r\n" + "\r\n"
						+ "              </div>\r\n" + "              \r\n" + "            </div>\r\n"
						+ "            \r\n" + "          </div>\r\n" + "          \r\n" + "        </div>\r\n"
						+ "      </div>\r\n" + "\r\n" + "\r\n" + "  </div>\r\n" + "  \r\n" + "  ");

				chartBuffer.append("<script>\r\n" + "  var ctx = document.getElementById(\"DeskChart" + j
						+ "0\").getContext('2d');" + "  var myChart = new Chart(ctx, {\r\n"
						+ "    type: 'doughnut',\r\n" + "    data: {  labels: [\"< 1s\", \"1s ~ 3s\", \"> 3s\"],\r\n"
						+ "      \r\n" + "      datasets: [{\r\n" + "        backgroundColor: [\r\n");

				int scoreValueFirst = Integer.parseInt(FCPScorePercent.get(0));
				int scoreValueSecond = Integer.parseInt(FCPScorePercent.get(1));
				int scoreValueThird = Integer.parseInt(FCPScorePercent.get(2));
				chartBuffer.append("          \"#0CCE6B\",\r\n");
				chartBuffer.append("          \"#FFA400\",\r\n");
				chartBuffer.append("          \"#FF4E42\",\r\n");
				chartBuffer.append("        ],\r\n" + "        data:");
				if (scoreValueFirst > 0)
					chartBuffer.append(" [" + FCPScorePercent.get(0));
				chartBuffer.append(",");
				if (scoreValueSecond > 0)
					chartBuffer.append(FCPScorePercent.get(1));
				chartBuffer.append(",");
				if (scoreValueThird > 0)
					chartBuffer.append(FCPScorePercent.get(2));
				chartBuffer.append("]\r\n" + "      }]\r\n" + "    },\r\n" + "    options : {        \r\n"
						+ "   cutoutPercentage: 60,\r\n" + "   legend: {\r\n" + "   display:false\r\n"
						+ "   }, plugins:{\r\n" + "    datalabels: {\r\n" + "    align: 'center',\r\n"
						+ "    anchor: 'center',\r\n" + "    color:'#FFF',\r\n" + "    display: true\r\n" + "}}\r\n"
						+ "  }\r\n" + "  });\r\n" + "  </script>");
				chartBuffer.append("<script>\r\n" + "var ctx = document.getElementById(\"DeskChart" + j
						+ "1\").getContext('2d');" + "  var myChart = new Chart(ctx, {\r\n"
						+ "    type: 'doughnut',\r\n" + "    data: { labels: [\"< 2.5s\", \"2.5s ~ 4s\", \"> 4s\"],\r\n"
						+ "      \r\n" + "      datasets: [{\r\n" + "        backgroundColor: [\r\n");
				int LCPscoreValueFirst = Integer.parseInt(LCPScorePercent.get(0));
				int LCPscoreValueSecond = Integer.parseInt(LCPScorePercent.get(1));
				int LCPscoreValueThird = Integer.parseInt(LCPScorePercent.get(2));
				chartBuffer.append("          \"#0CCE6B\",\r\n");
				chartBuffer.append("          \"#FFA400\",\r\n");
				chartBuffer.append("          \"#FF4E42\",\r\n");

				chartBuffer.append("        ],\r\n" + "        data:");

				if (LCPscoreValueFirst > 0)
					chartBuffer.append(" [" + LCPScorePercent.get(0));
				chartBuffer.append(",");
				if (LCPscoreValueSecond > 0)
					chartBuffer.append(LCPScorePercent.get(1));
				chartBuffer.append(",");
				if (LCPscoreValueThird > 0)
					chartBuffer.append(LCPScorePercent.get(2));
				chartBuffer.append("]\r\n" + "      }]\r\n" + "    },\r\n" + "    options : {        \r\n"
						+ "   cutoutPercentage: 60,\r\n" + "   legend: {\r\n" + "   display:false\r\n"
						+ "   }, plugins:{\r\n" + "    datalabels: {\r\n" + "    align: 'center',\r\n"
						+ "    anchor: 'center',\r\n" + "    color:'#FFF',\r\n" + "    display: true\r\n" + "}}\r\n"
						+ "  }\r\n" + "  });\r\n" + "  </script>");
				chartBuffer.append(
						"<script>\r\n" + " var ctx = document.getElementById(\"DeskChart" + j + "2\").getContext('2d');"
								+ "  var myChart = new Chart(ctx, {\r\n" + "    type: 'doughnut',\r\n"
								+ "    data: {labels: [\"< 100ms\", \"100ms ~ 300ms\", \"> 300ms\"],\r\n" + "      \r\n"
								+ "      datasets: [{\r\n" + "        backgroundColor: [\r\n");
				int FIDscoreValueFirst = Integer.parseInt(FIDScorePercent.get(0));
				int FIDscoreValueSecond = Integer.parseInt(FIDScorePercent.get(1));
				int FIDscoreValueThird = Integer.parseInt(FIDScorePercent.get(2));
				chartBuffer.append("          \"#0CCE6B\",\r\n");
				chartBuffer.append("          \"#FFA400\",\r\n");
				chartBuffer.append("          \"#FF4E42\",\r\n");
				chartBuffer.append("        ],\r\n" + "        data:");

				if (FIDscoreValueFirst > 0)
					chartBuffer.append(" [" + FIDScorePercent.get(0));
				chartBuffer.append(",");
				if (FIDscoreValueSecond > 0)
					chartBuffer.append(FIDScorePercent.get(1));
				chartBuffer.append(",");
				if (FIDscoreValueThird > 0)
					chartBuffer.append(FIDScorePercent.get(2));
				chartBuffer.append("]\r\n" + "      }]\r\n" + "    },\r\n" + "    options : {        \r\n"
						+ "   cutoutPercentage: 60,\r\n" + "  legend: {\r\n" + "   display:false\r\n"
						+ "   },  plugins:{\r\n" + "    datalabels: {\r\n" + "    align: 'center',\r\n"
						+ "    anchor: 'center',\r\n" + "    color:'#FFF',\r\n" + "    display: true\r\n" + "}}\r\n"
						+ "  }\r\n" + "  });\r\n" + "  </script>");
				chartBuffer.append("<script>\r\n" + "var ctx = document.getElementById(\"DeskChart" + j
						+ "3\").getContext('2d');" + "  var myChart = new Chart(ctx, {\r\n"
						+ "    type: 'doughnut',\r\n" + "    data: {  labels: [\"<.1\", \".1 ~ .25\", \"> .25\"],\r\n"
						+ "      \r\n" + "      datasets: [{\r\n" + "        backgroundColor: [\r\n");
				int CLSscoreValueFirst = Integer.parseInt(CLSScorePercent.get(0));
				int CLSscoreValueSecond = Integer.parseInt(CLSScorePercent.get(1));
				int CLSscoreValueThird = Integer.parseInt(CLSScorePercent.get(2));
				chartBuffer.append("          \"#0CCE6B\",\r\n");
				chartBuffer.append("          \"#FFA400\",\r\n");
				chartBuffer.append("          \"#FF4E42\",\r\n");

				chartBuffer.append("        ],\r\n" + "        data:");

				if (CLSscoreValueFirst > 0)
					chartBuffer.append(" [" + CLSScorePercent.get(0));
				chartBuffer.append(",");
				if (CLSscoreValueSecond > 0)
					chartBuffer.append(CLSScorePercent.get(1));
				chartBuffer.append(",");
				if (CLSscoreValueThird > 0)
					chartBuffer.append(CLSScorePercent.get(2));
				chartBuffer.append("]\r\n" + "      }]\r\n" + "    },\r\n" + "    options : {        \r\n"
						+ "   cutoutPercentage: 60,\r\n" + " legend: {\r\n" + "   display:false\r\n"
						+ "   },   plugins:{\r\n" + "    datalabels: {\r\n" + "    align: 'center',\r\n"
						+ "    anchor: 'center',\r\n" + "    color:'#FFF',\r\n" + "    display: true\r\n" + "}}\r\n"
						+ "  }\r\n" + "  });\r\n" + "  </script>");

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void addMobileModelBuffer() {
		String reportName = PerfAPIClient.getReportName();
		List<String> mobilePagesList = new ArrayList<>();
		File file = new File(reportName);
		try {
			String content = FileUtils.readFileToString(file, "utf-8");
			JSONObject obj = new JSONObject(content);
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject deskLightJsonObj = (JSONObject) obj;
			JSONObject deskLightAuditsJsonObj = (JSONObject) obj;
			JSONObject deskAuditsFCPJsonObj = (JSONObject) obj;
			JSONObject deskAuditsLCPJsonObj = (JSONObject) obj;
			JSONObject deskAuditsTBTJsonObj = (JSONObject) obj;
			JSONObject deskAuditsSIJsonObj = (JSONObject) obj;
			JSONObject deskAuditsTTIJsonObj = (JSONObject) obj;
			JSONObject deskAuditsCLSJsonObj = (JSONObject) obj;
			JSONObject deskJsonObj = (JSONObject) obj;
			JSONObject deskLoadExpJsonObj = (JSONObject) obj;
			JSONObject deskLoadMetricesJsonObj = (JSONObject) obj;
			JSONObject deskFCPJsonObj = (JSONObject) obj;
			JSONObject deskLCPJsonObj = (JSONObject) obj;
			JSONObject deskFIDJsonObj = (JSONObject) obj;
			JSONObject deskCLSJsonObj = (JSONObject) obj;
			JSONObject deskScrnShotJsonObj = (JSONObject) obj;
			JSONObject deskScrnShotDetailJsonObj = (JSONObject) obj;

			List<String> pageNames = PerfAPIClient.getPageNamesList();

			for (int i = 0; i < pageNames.size(); i++) {
				if (pageNames.get(i).contains(": Mobile"))
					mobilePagesList.add(pageNames.get(i));
			}
			for (int j = 0; j < mobilePagesList.size(); j++) {
				deskJsonObj = (JSONObject) jsonObject.get(mobilePagesList.get(j));
				deskLoadExpJsonObj = (JSONObject) deskJsonObj.get("loadingExperience");
				deskLoadMetricesJsonObj = (JSONObject) deskLoadExpJsonObj.get("metrics");

				deskFCPJsonObj = (JSONObject) deskLoadMetricesJsonObj.get("FIRST_CONTENTFUL_PAINT_MS");
				String FCPscore = deskFCPJsonObj.get("percentile").toString();

				Double FCPscoreVal = Double.parseDouble(FCPscore);
				Double FCPPercentval = null;
				if (FCPscoreVal > 1000) {
					FCPscoreVal = FCPscoreVal / 1000;
					DecimalFormat df = new DecimalFormat("#.##");

					FCPscore = df.format(FCPscoreVal) + "sec";
				} else {
					DecimalFormat df = new DecimalFormat("#.##");

					FCPscore = df.format(FCPscoreVal) + "ms";
				}

				List<String> FCPScorePercent = new ArrayList<String>();
				JSONArray FCBJsonArray = deskFCPJsonObj.getJSONArray("distributions");
				for (int i = 0; i < FCBJsonArray.length(); i++) {
					JSONObject percentJobj = FCBJsonArray.getJSONObject(i);
					String FCPPercent = percentJobj.get("proportion").toString();
					FCPPercentval = (double) Math.round(Double.parseDouble(FCPPercent) * 100);
					long val = FCPPercentval.longValue();
					FCPScorePercent.add(Long.toString(val));

				}

				deskLCPJsonObj = (JSONObject) deskLoadMetricesJsonObj.get("LARGEST_CONTENTFUL_PAINT_MS");
				String LCPscore = deskLCPJsonObj.get("percentile").toString();

				Double LCPscoreVal = Double.parseDouble(LCPscore);
				Double LCPPercentval = null;
				if (LCPscoreVal > 1000) {
					LCPscoreVal = LCPscoreVal / 1000;
					DecimalFormat df = new DecimalFormat("#.##");

					LCPscore = df.format(LCPscoreVal) + "sec";
				} else {
					DecimalFormat df = new DecimalFormat("#.##");

					LCPscore = df.format(LCPscoreVal) + "ms";
				}

				List<String> LCPScorePercent = new ArrayList<String>();
				JSONArray LCBJsonArray = deskLCPJsonObj.getJSONArray("distributions");
				for (int i = 0; i < LCBJsonArray.length(); i++) {
					JSONObject percentJobj = LCBJsonArray.getJSONObject(i);
					String LCPPercent = percentJobj.get("proportion").toString();
					LCPPercentval = (double) Math.round(Double.parseDouble(LCPPercent) * 100);
					long val = LCPPercentval.longValue();
					LCPScorePercent.add(Long.toString(val));

				}

				deskFIDJsonObj = (JSONObject) deskLoadMetricesJsonObj.get("FIRST_INPUT_DELAY_MS");
				String FIDscore = deskFIDJsonObj.get("percentile").toString();

				Double FIDscoreVal = Double.parseDouble(FIDscore);
				Double FIDPercentval = null;
				if (FIDscoreVal > 1000) {
					FIDscoreVal = FIDscoreVal / 1000;
					DecimalFormat df = new DecimalFormat("#.##");

					FIDscore = df.format(FIDscoreVal) + "sec";
				} else {
					DecimalFormat df = new DecimalFormat("#.##");

					FIDscore = df.format(FIDscoreVal) + "ms";
				}

				List<String> FIDScorePercent = new ArrayList<String>();
				JSONArray FIDJsonArray = deskFIDJsonObj.getJSONArray("distributions");
				for (int i = 0; i < FIDJsonArray.length(); i++) {
					JSONObject percentJobj = FIDJsonArray.getJSONObject(i);
					String FIDPercent = percentJobj.get("proportion").toString();
					FIDPercentval = (double) Math.round(Double.parseDouble(FIDPercent) * 100);
					long val = FIDPercentval.longValue();
					FIDScorePercent.add(Long.toString(val));

				}

				deskCLSJsonObj = (JSONObject) deskLoadMetricesJsonObj.get("CUMULATIVE_LAYOUT_SHIFT_SCORE");
				String CLSscore = deskCLSJsonObj.get("percentile").toString();

				Double CLSscoreVal = Double.parseDouble(CLSscore);
				Double CLSPercentval = null;
				if (CLSscoreVal > 1000) {
					CLSscoreVal = CLSscoreVal / 1000;
					DecimalFormat df = new DecimalFormat("#.##");

					CLSscore = df.format(CLSscoreVal) + "sec";
				} else {
					DecimalFormat df = new DecimalFormat("#.##");

					CLSscore = df.format(CLSscoreVal) + "ms";
				}

				List<String> CLSScorePercent = new ArrayList<String>();
				JSONArray CLSJsonArray = deskCLSJsonObj.getJSONArray("distributions");
				for (int i = 0; i < CLSJsonArray.length(); i++) {
					JSONObject percentJobj = CLSJsonArray.getJSONObject(i);
					String CLSPercent = percentJobj.get("proportion").toString();
					CLSPercentval = (double) Math.round(Double.parseDouble(CLSPercent) * 100);
					long val = CLSPercentval.longValue();
					CLSScorePercent.add(Long.toString(val));

				}

				deskLightJsonObj = (JSONObject) deskJsonObj.get("lighthouseResult");
				deskLightAuditsJsonObj = (JSONObject) deskLightJsonObj.get("audits");

				deskAuditsFCPJsonObj = (JSONObject) deskLightAuditsJsonObj.get("first-contentful-paint");
				String FCPmode = deskAuditsFCPJsonObj.getString("scoreDisplayMode");
				String auditFCPScore = null;
				if (FCPmode.equalsIgnoreCase("numeric")) {
					auditFCPScore = deskAuditsFCPJsonObj.getString("displayValue");
				}

				deskAuditsLCPJsonObj = (JSONObject) deskLightAuditsJsonObj.get("largest-contentful-paint");
				String LCPmode = deskAuditsLCPJsonObj.getString("scoreDisplayMode");
				String auditLCPScore = null;
				if (LCPmode.equalsIgnoreCase("numeric")) {
					auditLCPScore = deskAuditsLCPJsonObj.getString("displayValue");
				}

				deskAuditsTBTJsonObj = (JSONObject) deskLightAuditsJsonObj.get("total-blocking-time");
				String TBTmode = deskAuditsTBTJsonObj.getString("scoreDisplayMode");
				String auditTBTScore = null;
				if (TBTmode.equalsIgnoreCase("numeric")) {
					auditTBTScore = deskAuditsTBTJsonObj.getString("displayValue");
				}

				deskAuditsSIJsonObj = (JSONObject) deskLightAuditsJsonObj.get("speed-index");
				String SImode = deskAuditsSIJsonObj.getString("scoreDisplayMode");
				String auditSIScore = null;
				if (SImode.equalsIgnoreCase("numeric")) {
					auditSIScore = deskAuditsSIJsonObj.getString("displayValue");
				}

				deskAuditsTTIJsonObj = (JSONObject) deskLightAuditsJsonObj.get("interactive");
				String TTImode = deskAuditsTTIJsonObj.getString("scoreDisplayMode");
				String auditTTIScore = null;
				if (TTImode.equalsIgnoreCase("numeric")) {
					auditTTIScore = deskAuditsTTIJsonObj.getString("displayValue");
				}

				deskAuditsCLSJsonObj = (JSONObject) deskLightAuditsJsonObj.get("cumulative-layout-shift");
				String CLSmode = deskAuditsCLSJsonObj.getString("scoreDisplayMode");
				String auditCLSScore = null;
				if (CLSmode.equalsIgnoreCase("numeric")) {
					auditCLSScore = deskAuditsCLSJsonObj.getString("displayValue");
				}

				deskScrnShotJsonObj = (JSONObject) deskLightAuditsJsonObj.get("final-screenshot");
				deskScrnShotDetailJsonObj = (JSONObject) deskScrnShotJsonObj.get("details");
				String scrnShotData = deskScrnShotDetailJsonObj.getString("data");
				String trimData = scrnShotData.replace("data:image/jpeg;base64,", "").trim();
				byte[] imageByteArray = Base64.decodeBase64(trimData);

				File scrnshotDir = new File(reportFilePath + "/screenshots");
				if (!scrnshotDir.exists())
					scrnshotDir.mkdir();
				File imgFile = new File(reportFilePath + "/screenshots/"
						+ mobilePagesList.get(j).replace(": Mobile", "").trim() + "_Mobile.png");
				OutputStream os = new FileOutputStream(imgFile);
				os.write(imageByteArray);

				modelBuffer.append(" <div class=\"modal fade\" id=" + "MobModel" + j + " role=\"dialog\">\r\n"
						+ "        <div class=\"modal-dialog\">\r\n" + "        \r\n"
						+ "          <!-- Modal content-->\r\n" + "          <div class=\"modal-content\">\r\n"
						+ "            <div class=\"modal-header\">\r\n"
						+ "              <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\r\n"
						+ "              <h5 class=\"modal-title\">"
						+ mobilePagesList.get(j).replace(": Mobile", "").trim() + " </h5>\r\n"
						+ "            </div>\r\n"
						+ "            <div class=\"modal-body\" style=\"padding: 20px;\">\r\n"
						+ "              <div style=\"color: #2a3f54; font-size: 14px; font-weight: bold;\">Field Data</div>\r\n"
						+ "              <div class=\"row\" style=\"margin-top: 10px;\">\r\n"
						+ "                  <div class=\"col-md-3 col-lg-3\">\r\n"
						+ "                    <div class=\"chart_lbl\">First Contentful Paint(FCP)</div>\r\n"
						+ "                    <div>\r\n" + "                        <canvas id=" + "MobChart" + j
						+ "0></canvas>\r\n" + "                    </div>\r\n");

				if (FCPscore.contains("ec")) {
					String FCBScoreTrim = FCPscore.replace("sec", "").trim();
					Double FCBScoreInt = Double.parseDouble(FCBScoreTrim);
					if (FCBScoreInt >= 0 && FCBScoreInt <= 2)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
					else if (FCBScoreInt >= 2 && FCBScoreInt <= 4)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FFA400;\">");
					else
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FF4E42;\">");
				} else {
					modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
				}
				modelBuffer.append(FCPscore + "</div>\r\n" + "                  </div> \r\n" + "                  \r\n"
						+ "                  <div class=\"col-md-3 col-lg-3\">\r\n"
						+ "                    <div class=\"chart_lbl\">Largest Contentful Paint(LCP)</div>\r\n"
						+ "                    <div>\r\n" + "                        <canvas id=" + "MobChart" + j
						+ "1></canvas>\r\n" + "                    </div>\r\n");

				if (LCPscore.contains("ec")) {
					String LCBScoreTrim = LCPscore.replace("sec", "").trim();
					Double LCBScoreInt = Double.parseDouble(LCBScoreTrim);
					if (LCBScoreInt >= 0 && LCBScoreInt <= 2)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
					else if (LCBScoreInt >= 2 && LCBScoreInt <= 4)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FFA400;\">");
					else
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FF4E42;\">");
				} else {
					modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
				}
				modelBuffer.append(LCPscore + "</div>\r\n" + "                  </div> \r\n" + "\r\n"
						+ "                  <div class=\"col-md-3 col-lg-3\">\r\n"
						+ "                    <div class=\"chart_lbl\">First Input Delay(FID)</div>\r\n"
						+ "                    <div>\r\n" + "                        <canvas id=" + "MobChart" + j
						+ "2></canvas>\r\n" + "                    </div>\r\n");
				if (FIDscore.contains("ec")) {
					String FIDScoreTrim = FIDscore.replace("sec", "").trim();
					Double FIDScoreInt = Double.parseDouble(FIDScoreTrim);
					if (FIDScoreInt >= 0 && FIDScoreInt <= 2)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
					else if (FIDScoreInt >= 2 && FIDScoreInt <= 4)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FFA400;\">");
					else
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FF4E42;\">");
				} else {
					modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
				}
				modelBuffer.append(FIDscore + "</div>\r\n" + "                  </div> \r\n" + "\r\n"
						+ "                  <div class=\"col-md-3 col-lg-3\">\r\n"
						+ "                    <div class=\"chart_lbl\">Cumulative Layout Shift(CLS)</div>\r\n"
						+ "                    <div>\r\n" + "                        <canvas id=" + "MobChart" + j
						+ "3></canvas>\r\n" + "                    </div>\r\n");

				if (CLSscore.contains("ec")) {
					String CLSScoreTrim = CLSscore.replace("sec", "").trim();
					Double CLSScoreInt = Double.parseDouble(CLSScoreTrim);
					if (CLSScoreInt >= 0 && CLSScoreInt <= 2)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
					else if (CLSScoreInt >= 2 && CLSScoreInt <= 4)
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FFA400;\">");
					else
						modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#FF4E42;\">");
				} else {
					modelBuffer.append("                    <div class=\"perc_style\" style=\"color:#0CCE6B;\">");
				}
				modelBuffer.append(CLSscore + "</div>\r\n" + "                  </div> \r\n"
						+ "              </div>\r\n" + "\r\n"
						+ "              <div style=\"color: #2a3f54; font-size: 14px; font-weight: bold;\">Lab Data</div>\r\n"
						+ "\r\n");
				modelBuffer
						.append("  <div class=\"row\" style=\"margin-top: 10px;\"><div class=\"col-md-4 col-lg-4\">\r\n"
								+ "                    <table style=\"width:100%\">\r\n"
								+ "                      <tr>\r\n"
								+ "                        <td style=\"color: #2a3f54;\">First Contentful Paint(FCP)</td>\r\n"
								+ "                        <td style=\"color: #FF4E42; font-weight: bold;\">");

				modelBuffer.append(auditFCPScore + "</td>\r\n" + "                      </tr>\r\n"
						+ "                      <tr>\r\n"
						+ "                        <td style=\"color: #2a3f54;\">Speed Index</td>\r\n"
						+ "                        <td style=\"color: #FF4E42; font-weight: bold;\">" + auditSIScore
						+ "</td>\r\n" + "                      </tr>\r\n" + "                    </table>\r\n"
						+ "                  </div>\r\n" + "\r\n"
						+ "                  <div class=\"col-md-4 col-lg-4\">\r\n"
						+ "                    <table style=\"width:100%\">\r\n" + "                      <tr>\r\n"
						+ "                        <td style=\"color: #2a3f54;\">Largest Contentful Paint(LCP)</td>\r\n"
						+ "                        <td style=\"color: #FF4E42; font-weight: bold;\">" + auditLCPScore
						+ "</td>\r\n" + "                      </tr>\r\n" + "                      <tr>\r\n"
						+ "                        <td style=\"color: #2a3f54;\">Time to Interactive</td>\r\n"
						+ "                        <td style=\"color: #FF4E42; font-weight: bold;\">" + auditTTIScore
						+ "</td>\r\n" + "                      </tr>\r\n" + "                    </table>\r\n"
						+ "                  </div>\r\n" + "\r\n"
						+ "                  <div class=\"col-md-4 col-lg-4\">\r\n"
						+ "                    <table style=\"width:100%\">\r\n" + "                      <tr>\r\n"
						+ "                        <td style=\"color: #2a3f54;\">Total Blocking Time</td>\r\n"
						+ "                        <td style=\"color: #FF4E42; font-weight: bold;\">" + auditTBTScore
						+ "</td>\r\n" + "                      </tr>\r\n" + "                      <tr>\r\n"
						+ "                        <td style=\"color: #2a3f54;\">Cumulative Layout Shift(CLS)</td>\r\n"
						+ "                        <td style=\"color: #FF4E42; font-weight: bold;\">" + auditCLSScore
						+ "</td>\r\n" + "                      </tr>\r\n" + "                    </table>\r\n"
						+ "                  </div>\r\n" + "\r\n" + "                \r\n" + "\r\n"
						+ "              </div>\r\n" + "              \r\n" + "            </div>\r\n"
						+ "            \r\n" + "          </div>\r\n" + "          \r\n" + "        </div>\r\n"
						+ "      </div>\r\n" + "\r\n" + "\r\n" + "  </div>\r\n" + "  \r\n" + "  ");

				chartBuffer.append("<script>\r\n" + "  var ctx = document.getElementById(\"MobChart" + j
						+ "0\").getContext('2d');" + "  var myChart = new Chart(ctx, {\r\n"
						+ "    type: 'doughnut',\r\n" + "    data: { labels: [\"< 1s\", \"1s ~ 3s\", \"> 3s\"],\r\n"
						+ "      \r\n" + "      datasets: [{\r\n" + "        backgroundColor: [\r\n");

				int scoreValueFirst = Integer.parseInt(FCPScorePercent.get(0));
				int scoreValueSecond = Integer.parseInt(FCPScorePercent.get(1));
				int scoreValueThird = Integer.parseInt(FCPScorePercent.get(2));
				chartBuffer.append("          \"#0CCE6B\",\r\n");
				chartBuffer.append("          \"#FFA400\",\r\n");
				chartBuffer.append("          \"#FF4E42\",\r\n");

				chartBuffer.append("        ],\r\n" + "        data:");

				if (scoreValueFirst > 0)
					chartBuffer.append(" [" + FCPScorePercent.get(0));
				chartBuffer.append(",");
				if (scoreValueSecond > 0)
					chartBuffer.append(FCPScorePercent.get(1));
				chartBuffer.append(",");
				if (scoreValueThird > 0)
					chartBuffer.append(FCPScorePercent.get(2));
				chartBuffer.append("]\r\n" + "      }]\r\n" + "    },\r\n" + "    options : {        \r\n"
						+ "   cutoutPercentage: 60,\r\n" + "  legend: {\r\n" + "   display:false\r\n"
						+ "   }, legend: {\r\n" + "   display:false\r\n" + "   },  plugins:{\r\n"
						+ "    datalabels: {\r\n" + "    align: 'center',\r\n" + "    anchor: 'center',\r\n"
						+ "    color:'#FFF',\r\n" + "    display: true\r\n" + "}}\r\n" + "  }\r\n" + "  });\r\n"
						+ "  </script>");
				chartBuffer.append(
						"<script>\r\n" + "var ctx = document.getElementById(\"MobChart" + j + "1\").getContext('2d');"
								+ "  var myChart = new Chart(ctx, {\r\n" + "    type: 'doughnut',\r\n"
								+ "    data: {  labels: [\"< 2.5s\", \"2.5s ~ 4s\", \"> 4s\"],\r\n" + "      \r\n"
								+ "      datasets: [{\r\n" + "        backgroundColor: [\r\n");

				int LCPscoreValueFirst = Integer.parseInt(LCPScorePercent.get(0));
				int LCPscoreValueSecond = Integer.parseInt(LCPScorePercent.get(1));
				int LCPscoreValueThird = Integer.parseInt(LCPScorePercent.get(2));
				chartBuffer.append("          \"#0CCE6B\",\r\n");
				chartBuffer.append("          \"#FFA400\",\r\n");
				chartBuffer.append("          \"#FF4E42\",\r\n");

				chartBuffer.append("        ],\r\n" + "        data:");

				if (LCPscoreValueFirst > 0)
					chartBuffer.append(" [" + LCPScorePercent.get(0));
				chartBuffer.append(",");
				if (LCPscoreValueSecond > 0)
					chartBuffer.append(LCPScorePercent.get(1));
				chartBuffer.append(",");
				if (LCPscoreValueThird > 0)
					chartBuffer.append(LCPScorePercent.get(2));
				chartBuffer.append("]\r\n" + "      }]\r\n" + "    },\r\n" + "    options : {        \r\n"
						+ "   cutoutPercentage: 60,\r\n" + "  legend: {\r\n" + "   display:false\r\n"
						+ "   },  plugins:{\r\n" + "    datalabels: {\r\n" + "    align: 'center',\r\n"
						+ "    anchor: 'center',\r\n" + "    color:'#FFF',\r\n" + "    display: true\r\n" + "}}\r\n"
						+ "  }\r\n" + "  });\r\n" + "  </script>");
				chartBuffer.append(
						"<script>\r\n" + " var ctx = document.getElementById(\"MobChart" + j + "2\").getContext('2d');"
								+ "  var myChart = new Chart(ctx, {\r\n" + "    type: 'doughnut',\r\n"
								+ "    data: {  labels: [\"< 100ms\", \"100ms ~ 300ms\", \"> 300ms\"],\r\n"
								+ "      \r\n" + "      datasets: [{\r\n" + "        backgroundColor: [\r\n");
				int FIDscoreValueFirst = Integer.parseInt(FIDScorePercent.get(0));
				int FIDscoreValueSecond = Integer.parseInt(FIDScorePercent.get(1));
				int FIDscoreValueThird = Integer.parseInt(FIDScorePercent.get(2));
				chartBuffer.append("          \"#0CCE6B\",\r\n");
				chartBuffer.append("          \"#FFA400\",\r\n");
				chartBuffer.append("          \"#FF4E42\",\r\n");

				chartBuffer.append("        ],\r\n" + "        data:");

				if (FIDscoreValueFirst > 0)
					chartBuffer.append(" [" + FIDScorePercent.get(0));
				chartBuffer.append(",");
				if (FIDscoreValueSecond > 0)
					chartBuffer.append(FIDScorePercent.get(1));
				chartBuffer.append(",");
				if (FIDscoreValueThird > 0)
					chartBuffer.append(FIDScorePercent.get(2));
				chartBuffer.append("]\r\n" + "      }]\r\n" + "    },\r\n" + "    options : {        \r\n"
						+ "   cutoutPercentage: 60,\r\n" + "   legend: {\r\n" + "   display:false\r\n"
						+ "   }, plugins:{\r\n" + "    datalabels: {\r\n" + "    align: 'center',\r\n"
						+ "    anchor: 'center',\r\n" + "    color:'#FFF',\r\n" + "    display: true\r\n" + "}}\r\n"
						+ "  }\r\n" + "  });\r\n" + "  </script>");
				chartBuffer.append("<script>\r\n" + "var ctx = document.getElementById(\"MobChart" + j
						+ "3\").getContext('2d');" + "  var myChart = new Chart(ctx, {\r\n"
						+ "    type: 'doughnut',\r\n" + "    data: { labels: [\"<.1\", \".1 ~ .25\", \"> .25\"],\r\n"
						+ "      \r\n" + "      datasets: [{\r\n" + "        backgroundColor: [\r\n");
				int CLSscoreValueFirst = Integer.parseInt(CLSScorePercent.get(0));
				int CLSscoreValueSecond = Integer.parseInt(CLSScorePercent.get(1));
				int CLSscoreValueThird = Integer.parseInt(CLSScorePercent.get(2));
				chartBuffer.append("          \"#0CCE6B\",\r\n");
				chartBuffer.append("          \"#FFA400\",\r\n");
				chartBuffer.append("          \"#FF4E42\",\r\n");

				chartBuffer.append("        ],\r\n" + "        data:");

				if (CLSscoreValueFirst > 0)
					chartBuffer.append(" [" + CLSScorePercent.get(0));
				chartBuffer.append(",");
				if (CLSscoreValueSecond > 0)
					chartBuffer.append(CLSScorePercent.get(1));
				chartBuffer.append(",");
				if (CLSscoreValueThird > 0)
					chartBuffer.append(CLSScorePercent.get(2));
				chartBuffer.append("]\r\n" + "      }]\r\n" + "    },\r\n" + "    options : {        \r\n"
						+ "   cutoutPercentage: 60,\r\n" + "   legend: {\r\n" + "   display:false\r\n"
						+ "   }, plugins:{\r\n" + "    datalabels: {\r\n" + "    align: 'center',\r\n"
						+ "    anchor: 'center',\r\n" + "    color:'#FFF',\r\n" + "    display: true\r\n" + "}}\r\n"
						+ "  }\r\n" + "  });\r\n" + "  </script>");

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void copyFolder(File sourceFolder, File destinationFolder) throws IOException {
		if (sourceFolder.isDirectory()) {
			if (!destinationFolder.exists()) {
				destinationFolder.mkdir();
			}
			String files[] = sourceFolder.list();
			for (String file : files) {
				File srcFile = new File(sourceFolder, file);
				File destFile = new File(destinationFolder, file);
				copyFolder(srcFile, destFile);
			}
		} else

		{
			Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}

	}

	public static void main(String args[]) {
		addListWebBuffer();
		addListMobBuffer();
		addDesktopModelBuffer();
		addMobileModelBuffer();
	}
}
