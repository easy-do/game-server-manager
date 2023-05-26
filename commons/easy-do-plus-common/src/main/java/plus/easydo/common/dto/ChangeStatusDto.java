package plus.easydo.common.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/17
 */
@Data
public class ChangeStatusDto implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer status;

    private Long updateUserId;
}
