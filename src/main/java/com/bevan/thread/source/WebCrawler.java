package com.bevan.thread.source;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// import okhttp3.OkHttpClient;
// import okhttp3.Request;
// import okhttp3.Response;
/**
 * @author zbf
 * @since 2024/6/11 下午6:28
 *
 * 多线程网络爬虫
 * 题目：编写一个多线程网络爬虫，从一个给定的URL开始，下载网页内容并提取所有链接。
 * 然后继续抓取这些链接的网页内容。使用线程池和阻塞队列来协调任务。
 *
 * 问题：没有okHttp3包
 */
public class WebCrawler {
    private static final int THREAD_COUNT = 10;
    private static final int MAX_PAGES_TO_CRAWL = 100;
    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private static final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
    // private static final OkHttpClient client = new OkHttpClient();
    private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

    public static void main(String[] args) throws InterruptedException {
        String startUrl = "http://example.com";
        queue.add(startUrl);
        visitedUrls.add(startUrl);

        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.execute(new CrawlerTask());
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
    }

    static class CrawlerTask implements Runnable {
        @Override
        public void run() {
            while (!queue.isEmpty() && visitedUrls.size() < MAX_PAGES_TO_CRAWL) {
                String url = queue.poll();
                if (url == null) continue;

                // try {
                    // String content = downloadPage(url);
                    // extractLinks(content).forEach(link -> {
                    //     if (visitedUrls.add(link)) {
                    //         queue.add(link);
                    //     }
                    // });
                // } catch (IOException e) {
                //     e.printStackTrace();
                // }
            }
        }

        // private String downloadPage(String url) throws IOException {
            // Request request = new Request.Builder().url(url).build();
            // try (Response response = client.newCall(request).execute()) {
            //     return response.body().string();
            // }
        // }

        private Set<String> extractLinks(String content) {
            Set<String> links = new HashSet<>();
            Pattern pattern = Pattern.compile("href=\"(http[^\"]+)\"");
            Matcher matcher = pattern.matcher(content);

            while (matcher.find()) {
                links.add(matcher.group(1));
            }
            return links;
        }
    }
}
