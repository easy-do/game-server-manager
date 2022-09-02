package game.server.manager.server.service;

import game.server.manager.server.dto.AuditDto;

/**
 * @author laoyu
 * @version 1.0
 * @date 2022/7/23
 */

public interface AuditService {

    /**
     * 审核
     *
     * @param dto dto
     * @return boolean
     * @author laoyu
     * @date 2022/7/23
     */
    boolean audit(AuditDto dto);

    /**
     * 提交审核
     *
     * @param auditDto auditDto
     * @return boolean
     * @author laoyu
     * @date 2022/7/23
     */
    boolean commitAudit(AuditDto auditDto);
}
