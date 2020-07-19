package cn.lihongjie.tocgen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.kohsuke.github.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
public class Main {


    private static String login = "lihongjie0209";
    private static String repo = "lihongjie0209/myblog";

    public static void main(String[] args) throws IOException, TemplateException {


        GHRepository repository = GitHub.connect(login, args[0]).getRepository(repo);
        HashMap<String, List<GHIssue>> map = groupByLabel(repository);
        saveToFile(map);


    }

    private static void saveToFile(HashMap<String, List<GHIssue>> map) throws IOException, TemplateException {
        //创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 告诉config对象模板文件存放的路径。
        configuration.setClassLoaderForTemplateLoading(Main.class.getClassLoader(), "template");
        // 设置config的默认字符集。一般是utf-8
        configuration.setDefaultEncoding("utf-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
        //从config对象中获得模板对象。需要制定一个模板文件的名字。
        Template template = configuration.getTemplate("README.ftl");
        Writer out = new OutputStreamWriter(new FileOutputStream(new File("./README.md")), StandardCharsets.UTF_8);
        //调用模板对象的process方法生成静态文件。需要两个参数数据集和writer对象。
        HashMap<String, Object> root = new HashMap<>();
        root.put("data", map);
        template.process(root, out);
        //关闭writer对象。
        out.flush();
        out.close();
    }

    private static HashMap<String, List<GHIssue>> groupByLabel(GHRepository repository) throws IOException {
        List<GHIssue> ghIssues = repository.listIssues(GHIssueState.ALL).toList();

        HashMap<String, List<GHIssue>> map = new HashMap<>();
        for (GHIssue ghIssue : ghIssues) {
            Collection<GHLabel> labels = ghIssue.getLabels();

            log.info("发现 " + ghIssue.toString());

            for (GHLabel label : labels) {

                map.putIfAbsent(label.getName(), new ArrayList<>());
                map.get(label.getName()).add(ghIssue);
            }

        }

        for (String label : map.keySet()) {
            map.get(label).sort(Comparator.comparing(item -> {
                try {
                    return item.getCreatedAt();
                } catch (IOException e) {
                    return new Date();
                }
            }));
        }
        return map;
    }
}
