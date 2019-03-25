package top.yeonon.adsponsor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author yeonon
 * @date 2019/3/22 0022 17:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ad_creative")
public class AdCreative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 该创意的主类型（文字，视频或者图片等）
     */
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * 物料类型（即图片的话可以是png、jpeg等）
     */
    @Column(name = "material_type", nullable = false)
    private Integer materialType;

    /**
     * 物料的结构尺寸
     */
    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "size", nullable = false)
    private Long size;

    /**
     * 持续时间（只有在物料是视频的情况下才不为0）
     */
    @Column(name = "duration", nullable = false)
    private Integer duration;

    /**
     * 审核状态
     */
    @Column(name = "audit_status", nullable = false)
    private Integer auditStatus;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 物料的实际地址
     */
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;
}
