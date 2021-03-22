package libra.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据库业务对象
 * @author libra
 */
//lombok注释，复合注释，为类生成@getter、@setter@RequiredArgsConstructor、@ToString、@EqualsAndHashCode方法
@Data
//lombok注释，为类生成相对略微复杂的构建器API，简单来说就是可以像jQuery那样进行链式编程
@Builder
//lombok注释，为类生成一个无参构造函数
@NoArgsConstructor
//lombok注释，为类生成一个全参构造函数
@AllArgsConstructor
public class Table_data implements Serializable{
	/**  */
	private static final long serialVersionUID = 3950363227703518584L;
	private Integer id;
	private String info;
	private String createTime;
	private String srcFrom_;
}
