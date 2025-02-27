# 知识库基础表
CREATE TABLE knowledge_bases (
                                 id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '知识库主键 ID',
                                 user_id BIGINT UNSIGNED NOT NULL COMMENT '所属用户 ID',
                                 name VARCHAR(255) NOT NULL COMMENT '知识库名称',
                                 description VARCHAR(500) NULL COMMENT '知识库描述',
                                 visibility ENUM('private', 'public') DEFAULT 'private' NOT NULL COMMENT '可见性 (private/public)',
                                 cover_image_url VARCHAR(500) NULL COMMENT '知识库封面图 URL',
                                 created_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 updated_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 is_deleted TINYINT(1) DEFAULT 0 NOT NULL COMMENT '是否已删除 (0: 否, 1: 是)'
) COMMENT='知识库表，用于存储用户的知识库信息';

-- 索引
CREATE INDEX idx_user_id ON knowledge_bases (user_id);
CREATE INDEX idx_visibility ON knowledge_bases (visibility);