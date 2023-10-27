package com.hhc.blogs.service;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.mining.cluster.ClusterAnalyzer;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RecommendService {

    private static final Logger LOG = LoggerFactory.getLogger(RecommendService.class);

    public void test(){

        // 分词
        List<Term> termList = StandardTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？徐先生还具体帮助他确定了把画雄鹰、松鼠和麻雀作为主攻目标。");
        LOG.info("分词返回值{}", termList);

        // 挑选出名词
        List<String> wordList = new ArrayList<>();
        for (Term term: termList) {
            if (term.nature.equals(Nature.n) || term.nature.equals(Nature.nz)){
                wordList.add(term.word);
            }
        }
        LOG.info("分词结果：{}", termList);
        LOG.info("名词结果：{}", wordList);

        // 添加文档
        ClusterAnalyzer<String> analyzer = new ClusterAnalyzer<String>();
        analyzer.addDocument("赵一", "流行, 流行, 流行, 流行, 流行, 流行, 流行, 流行, 流行, 流行, 蓝调, 蓝调, 蓝调, 蓝调, 蓝调, 蓝调, 摇滚, 摇滚, 摇滚, 摇滚");
        analyzer.addDocument("钱二", "爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲");
        analyzer.addDocument("张三", "古典, 古典, 古典, 古典, 民谣, 民谣, 民谣, 民谣");
        analyzer.addDocument("李四", "爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 爵士, 金属, 金属, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲, 舞曲");
        analyzer.addDocument("王五", "流行, 流行, 流行, 流行, 摇滚, 摇滚, 摇滚, 嘻哈, 嘻哈, 嘻哈");
        analyzer.addDocument("马六", "古典, 古典, 古典, 古典, 古典, 古典, 古典, 古典, 摇滚");

        // 对分词结果进行添加
        String document = new String();
        for (String word: wordList) {
            document.concat("," + word);
        }
        analyzer.addDocument("hhc", document);

        // 聚类：返回指定数量的簇构成的集合，每个簇是一个Set，内部元素为文档的id
        List<Set<String>> kmeans = analyzer.kmeans(3);
        LOG.info("kmeans结果：{}", kmeans);

        // 关键词提取
        String content = "程序员(英文Programmer)是从事程序开发、维护的专业人员。一般将程序员分为程序设计人员和程序编码人员，但两者的界限并不非常清楚，特别是在中国。软件从业人员分为初级程序员、高级程序员、系统分析员和项目经理四大类。";
        List<String> keywordList = HanLP.extractKeyword(content, 5);
        System.out.println(keywordList);

        // 地名识别
        String[] testCase = new String[]{
                "武胜县新学乡政府大楼门前锣鼓喧天",
                "蓝翔给宁夏固原市彭阳县红河镇黑牛沟村捐赠了挖掘机",
        };
        Segment segment = HanLP.newSegment().enablePlaceRecognize(true);
        for (String sentence : testCase)
        {
            List<Term> termListPlace = segment.seg(sentence);
            System.out.println(termListPlace);
        }
    }

}
