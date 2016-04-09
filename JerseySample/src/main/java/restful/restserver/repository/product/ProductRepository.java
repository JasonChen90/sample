package restful.restserver.repository.product;

import org.springframework.context.annotation.Configuration;

import restful.restserver.model.Product;
import restful.restserver.repository.util.GenericRepository;

@Configuration
public interface ProductRepository extends GenericRepository<Product, String> {

}
