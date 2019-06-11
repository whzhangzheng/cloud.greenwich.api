package cn.zhangz.common.appLog.controller;

import cn.zhangz.common.api.model.tree.Tree;
import cn.zhangz.common.appLog.model.LogFileVO;
import cn.zhangz.common.utils.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

@Controller
@RequestMapping("/publicservice/logFile")
@Slf4j
public class LogFileController {

    @Value("${logging.path:/tmp/log}")
    private String path;

    @RequestMapping("/list")
    @ResponseBody
    public List<Tree<LogFileVO>> getLogFileList(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin","*");
        List<LogFileVO> files = new ArrayList<>();
        LogFileVO.readFile(path,files);
        return buildTree(files);
    }

    @RequestMapping("/down")
    @ResponseBody
    public void downFile(@RequestParam("path") String path, HttpServletResponse response){
        OutputStream out = null;
        FileInputStream in = null;
        try {
            // 查询数据
            File file = new File(path);

            response.reset(); // 清空response
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(file.getName().getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setContentType("text/plain");
            response.addHeader("Access-Control-Allow-Origin","*");
            out = response.getOutputStream();

            in = new FileInputStream(file);

            int n = 0;
            byte b[] = new byte[4096];
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            out.flush();
        } catch (Exception e) {
            log.error("文件下载异常"+e.getMessage());
        } finally {
            IOUtils.closeQuietly(in, out);
        }
    }

    /**
     * 构造树
     */
    private List<Tree<LogFileVO>> buildTree(List<LogFileVO> dictVOs){
        List<Tree<LogFileVO>> trees = new ArrayList<>();
        Tree<LogFileVO> tree = null;
        Map<String, Object> attributes = null;
        Map<String, Object> state = null;
        for(LogFileVO tmpDict : dictVOs){
            tree = new Tree<LogFileVO>();
            tree.setId(String.valueOf(tmpDict.getId()));

            tree.setParentId(String.valueOf(tmpDict.getParentId()));
            tree.setText(tmpDict.getName());
            // 添加属性
            attributes = new HashMap<>(1);
            attributes.put("select", "select");
            //将路径写在对象中
            attributes.put("value", tmpDict.getAbsolutePath());
            attributes.put("isFile", tmpDict.isFile());
            tree.setAttributes(attributes);

            // 添加状态: 选中
            state = new HashMap<>(1);
            //state.put("selected", true);
            state.put("selected", false);
            tree.setState(state);
            trees.add(tree);
        }
        Comparator<Tree> comparator = Comparator.comparing(Tree::getText);
        trees.sort(comparator.reversed());
        // 默认顶级菜单为０，根据数据库实际情况调整
        List<Tree<LogFileVO>> list = TreeUtils.buildList(trees, "0");
        return list;
    }
}
