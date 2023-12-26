package entity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * (TwAuthExtend)实体类
 *
 * @author makejava
 * @since 2023-12-23 18:18:46
 */
@Data
@TableName("tw_auth_extend")
public class TwAuthExtend implements Serializable {
    private static final long serialVersionUID = -37923688152965314L;
/**
     * 用户id
     */
    private String groupId;
/**
     * 扩展表中数据的id
     */
    private String extendId;
/**
     * 扩展类型标识 1:栏目分类权限;2:模型权限
     */
    private String type;



}

