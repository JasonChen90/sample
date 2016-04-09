package restful.restserver.repository.product.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowCallbackHandler;

import restful.restserver.model.Product;
import restful.restserver.repository.product.ProductRepository;
import restful.restserver.repository.util.AbstractRepository;

/**
 * @author JasonChen1
 */
@Configuration
public class ProductRepositoryImpl extends AbstractRepository implements ProductRepository {

	@Override
	public Product getById(String skuCode) {
		String sql = "SELECT * FROM TABLE_NAME T WHERE T.PRODUCT_ID = :PRODUCT_ID";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("PRODUCT_ID", skuCode);
		final Product product = new Product();
		this.namedParameterJdbcTemplate.query(sql, paramMap, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				//wrap sku
				product.setProductId(rs.getString("SKU_CODE"));
				}
		});
		return product;
	}

}
