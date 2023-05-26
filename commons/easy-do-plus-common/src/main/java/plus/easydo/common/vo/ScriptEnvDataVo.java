package plus.easydo.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 应用变量
 * @author yuzhanfeng
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptEnvDataVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 变量名称
     */
    private String envName;

    /**
     * 变量key
     */
    private String envKey;

    /**
     * shell脚本取参key
     */
    private String shellKey;

    /**
     * 变量参数-默认值
     */
    private String envValue;

    /**
     * 变量类型
     */
    private String envType;

    /**
     * 描述
     */
    private String description;

}
