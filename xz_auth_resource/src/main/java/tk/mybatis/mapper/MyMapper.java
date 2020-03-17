package tk.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author xz
 * @ClassName MyMapper
 * @Description 通用Mapper
 * @date 2020/2/14 0014 12:53
 **/
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
