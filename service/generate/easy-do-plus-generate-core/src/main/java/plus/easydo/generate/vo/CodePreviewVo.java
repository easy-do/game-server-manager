package plus.easydo.generate.vo;

import cn.hutool.core.lang.tree.Tree;
import lombok.Builder;
import lombok.Data;

import javax.swing.tree.TreeNode;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description TODO
 * @date 2023/5/27
 */
@Data
@Builder
public class CodePreviewVo {

    private List<Tree<Integer>> filePathTree;

    private List<Code> codes;

    @Data
    @Builder
    public static class Code{
        /**
         * 类型
         */
        private String templateType;

        /**
         * 模板名称
         */
        private String templateName;


        /**
         * 文件名
         */
        private String fileName;

        /**
         * 文件路径
         */
        private String filePath;

        /**
         * 文件路径
         */
        private String code;
    }

}
