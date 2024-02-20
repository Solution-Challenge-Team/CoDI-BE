package koders.codi.domain.jobInfo;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
//import javax.swing.text.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobInfoService {

    private static String baseUrl = "https://www.worktogether.or.kr/empInfo/empInfoBbs/empScrapInfoList.do?pageIndex=";
    private static String base = "https://www.worktogether.or.kr/";

    public List<JobInfo> getJobs() throws IOException {
        List<JobInfo> jobInfoList = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            String url = baseUrl + i + "&searchText=&boardNo=&pageUrl=&siteClcd=&searchType=&pageUnit=10";

            try {
                Document doc = Jsoup.connect(url).get();
                Elements rows = doc.select("tbody tr");

                for (Element row : rows) {
                    JobInfo jobInfo = new JobInfo();
                    Elements tds = row.select("td");

                    Element jobTypeElement = tds.get(1);
                    String jobType = jobTypeElement.text();
                    jobInfo.setJobType(jobType);

                    Element titleLinkElement = row.select("td.title a").first();
                    String link = titleLinkElement.attr("href");
                    String newUrl = base + link;
                    String titleText = titleLinkElement.text();
                    jobInfo.setUrl(newUrl);
                    jobInfo.setSubject(titleText);

                    Element registerElement = tds.get(3);
                    String register = registerElement.text();
                    jobInfo.setRegister(register);

                    Element dateElement = tds.get(4);
                    String date = dateElement.text();
                    jobInfo.setEndDate(date);

                    jobInfoList.add(jobInfo);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return jobInfoList;
    }


//    private static String Job_URL = "https://www.ablejob.co.kr/";

    //장애인잡
    //서밋, 긴급, 일반 / 서밋은 위치정보 x

//    public List<JobInfo> getJobInfos() throws IOException {
//        WebDriver driver = WebDriverUtil.getChromeDriver();
//        List<WebElement> webElements = new ArrayList<>();
//        List<JobInfo> jobInfoList = new ArrayList<>();
//
//        JavascriptExecutor executor = (JavascriptExecutor) driver;
//
//        try {
//            driver.get(Job_URL);
//
//            WebElement summits = driver.findElement(By.className("ad_list"));
//
//            for (WebElement ad : summits.findElements(By.className("ad"))) {
//                JobInfo jobInfo = new JobInfo();
//
//                WebElement companyNameElement = ad.findElement(By.className("companyName"));
//                String company = companyNameElement.getText();
//                jobInfo.setCompany(company);
//
//                WebElement subjectElement = ad.findElement(By.className("subject"));
//                String subject = subjectElement.getText();
//                jobInfo.setSubject(subject);
//
//                WebElement endDateElement = ad.findElement(By.className("endDate"));
//                String endDate = endDateElement.getText();
//                jobInfo.setEndDate(endDate);
//
////                WebElement aTag = ad.findElement(By.tagName("a"));
////                String url = aTag.getAttribute("href");
////                jobInfo.setUrl(url);
//
////                // JavaScript 함수 실행하여 이벤트 발생시키기
//                WebElement aTag = ad.findElement(By.tagName("a"));
//                String url = aTag.getAttribute("href").replaceAll("[^\\d]", "");
//////                JavascriptExecutor executor = (JavascriptExecutor) driver;
//                executor.executeScript("recruitView('" + url + "')");
////
//                // 새로운 페이지로 이동 후 URL 가져오기
//                String newUrl = driver.getCurrentUrl();
//                jobInfo.setUrl(newUrl);
//
//                jobInfoList.add(jobInfo);
//            }
//
//            List<WebElement> urgents = driver.findElements(By.cssSelector(".ad_list_wrap .ad_list.urgent > li.ad"));
//
//            for(WebElement urgent : urgents) {
//                JobInfo jobInfo = new JobInfo();
//
//                String companyName = urgent.findElement(By.cssSelector(".companyName.drop")).getText().trim();
//                String subject = urgent.findElement(By.cssSelector(".subject")).getText().trim();
//                String location = urgent.findElement(By.cssSelector(".location")).getText().trim();
//                String endDate = urgent.findElement(By.cssSelector(".endDate")).getText().trim();
//                String jobUrl = urgent.findElement(By.cssSelector("a[href]")).getAttribute("href").replaceAll("[^\\d]", "").trim();
//
////                // 만약 jobUrl이 JavaScript 형식이면 JavaScript를 실행하여 실제 URL을 가져옴
////                if (jobUrl.startsWith("javascript:")) {
////                    String uid = urgent.findElement(By.cssSelector("a.aTag")).getAttribute("data-uid").trim();
////
////                    // JavaScript를 실행하여 URL 추출
////                    String script = "var el = document.querySelector('.aTag[data-uid=\"" + uid + "\"]');" +
////                            "var url = el.getAttribute('href');" +
////                            "return url;";
////                    jobUrl = (String) executor.executeScript(script);
////                }
//
//                jobInfo.setCompany(companyName);
//                jobInfo.setSubject(subject);
//                jobInfo.setLocation(location);
//                jobInfo.setEndDate(endDate);
////                jobInfo.setUrl(jobUrl);
//
//                //js함수 실행하여 이벤트 발생시키기
//                executor.executeScript("recruitView('" + jobUrl + "')");
//                // 새로운 페이지로 이동 후 URL 가져오기
//                String newUrl = driver.getCurrentUrl();
//                jobInfo.setUrl(newUrl);
////
//                jobInfoList.add(jobInfo);
//            }
//
//            List<WebElement> normals = driver.findElements(By.cssSelector(".ad_list.normal.clearfix > li.ad"));
//
//            for(WebElement normal : normals) {
//                JobInfo jobInfo = new JobInfo();
//
//                String companyName = normal.findElement(By.cssSelector(".companyName.drop")).getText().trim();
//                String subject = normal.findElement(By.cssSelector(".subject")).getText().trim();
//                String location = normal.findElement(By.cssSelector(".location")).getText().trim();
//                String endDate = normal.findElement(By.cssSelector(".endDate")).getText().trim();
//                String jobUrl = normal.findElement(By.cssSelector("a[href]")).getAttribute("href").replaceAll("[^\\d]", "").trim();
//
////                // 만약 jobUrl이 JavaScript 형식이면 JavaScript를 실행하여 실제 URL을 가져옴
////                if (jobUrl.startsWith("javascript:")) {
////                    String uid = normal.findElement(By.cssSelector("a.aTag")).getAttribute("data-uid").trim();
////
////                    // JavaScript를 실행하여 URL 추출
////                    String script = "var el = document.querySelector('.aTag[data-uid=\"" + uid + "\"]');" +
////                            "var url = el.getAttribute('href');" +
////                            "return url;";
////                    jobUrl = (String) executor.executeScript(script);
////                }
//
//                jobInfo.setCompany(companyName);
//                jobInfo.setSubject(subject);
//                jobInfo.setLocation(location);
//                jobInfo.setEndDate(endDate);
////                jobInfo.setUrl(jobUrl);
//
//                //js함수 실행하여 이벤트 발생시키기
//                executor.executeScript("recruitView('" + jobUrl + "')");
//                // 새로운 페이지로 이동 후 URL 가져오기
//                String newUrl = driver.getCurrentUrl();
//                jobInfo.setUrl(newUrl);
//
//                jobInfoList.add(jobInfo);
//            }
//
//            JobInfo jobInfo = new JobInfo();
//
//            String moreUrl = driver.findElement(By.cssSelector("a.productTag")).getAttribute("href");
//            String subject = "더보기";
//            jobInfo.setSubject(subject);
//            jobInfo.setUrl(moreUrl);
//
//            jobInfoList.add(jobInfo);
//        } finally {
//            driver.quit();
//        }
//
//        return jobInfoList;
//    }

//    public List<JobInfo> getJobs() throws IOException {
//        List<JobInfo> jobInfoList = new ArrayList<>();
//        Document document = Jsoup.connect(Job_URL).get();
//
//        Elements summits = document.select("ul.ad_list li.ad");
//
//        for (Element jobElement : summits) {
//            JobInfo jobInfo = new JobInfo();
//
////            // 이미지 URL 추출
////            String image = jobElement.select("span.logo img").first().attr("src");
////            jobInfo.setImageUrl(image);
//
//            // 회사 이름 추출
//            String company = jobElement.select("span.companyName").first().text();
//            jobInfo.setCompany(company);
//
//            // 주제 추출
//            String subject = jobElement.select("span.subject").first().text();
//            jobInfo.setSubject(subject);
//
//            // 마감일 추출
//            String endDate = jobElement.select("span.endDate").first().text();
//            jobInfo.setEndDate(endDate);
//
//            // URL 추출
//            String url = jobElement.select("a").first().attr("href");
//            jobInfo.setUrl(url);
//
//            jobInfoList.add(jobInfo);
//        }
//
//        Elements urgents = document.select("ul.ad_list.urgent li.ad");
//
//        for (Element jobElement : urgents) {
//            JobInfo jobInfo = new JobInfo();
//
////            // 이미지 URL 추출
////            String imageUrl = jobElement.select("a img").attr("src");
////            jobInfo.setImageUrl(imageUrl);
//
//            // 회사 이름 추출
//            String company = jobElement.select("span.companyName").text();
//            jobInfo.setCompany(company);
//
//            // 주제 추출
//            String subject = jobElement.select("span.subject").text();
//            jobInfo.setSubject(subject);
//
//            // 위치 추출
//            String location = jobElement.select("span.location").text();
//            jobInfo.setLocation(location);
//
//            // 마감일 추출
//            String endDate = jobElement.select("span.endDate").text();
//            jobInfo.setEndDate(endDate);
//
//            // URL 추출
//            String Url = jobElement.select("a").attr("href");
//            jobInfo.setUrl(Url);
//
//            jobInfoList.add(jobInfo);
//        }
//
//        Elements normals = document.select("ul.ad_list.normal li.ad");
//
//        for (Element jobElement : urgents) {
//            JobInfo jobInfo = new JobInfo();
//
////            // 이미지 URL 추출
////            String imageUrl = jobElement.select("a img").attr("src");
////            jobInfo.setImageUrl(imageUrl);
//
//            // 회사 이름 추출
//            String company = jobElement.select("span.companyName").text();
//            jobInfo.setCompany(company);
//
//            // 주제 추출
//            String subject = jobElement.select("span.subject").text();
//            jobInfo.setSubject(subject);
//
//            // 위치 추출
//            String location = jobElement.select("span.location").text();
//            jobInfo.setLocation(location);
//
//            // 마감일 추출
//            String endDate = jobElement.select("span.endDate").text();
//            jobInfo.setEndDate(endDate);
//
//            // URL 추출
//            String Url = jobElement.select("a").attr("href");
//            jobInfo.setUrl(Url);
//
//            jobInfoList.add(jobInfo);
//        }
//
//        return jobInfoList;
//    }

//    Elements contents = document.select("section.con div.inner div.box_board dd.list_board ul li");
//
//        for (Element content : contents) {
//        JobInfo jobInfo = new JobInfo();
//        jobInfo.setImage(content.select("span.title img").attr("abs:src")); // 이미지
//        jobInfo.setSubject(content.select("span.title").text());             // 제목
//        jobInfo.setDate(content.select("span.date").text());                 // 날짜
//        jobInfo.setUrl(content.select("a").attr("abs:href"));                // 링크
//        jobInfoList.add(jobInfo);
//
//        jobInfoList.add(jobInfo);
//    }


}
