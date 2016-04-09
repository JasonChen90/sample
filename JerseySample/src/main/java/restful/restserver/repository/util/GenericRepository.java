package restful.restserver.repository.util;


public interface GenericRepository<T,K> {
	
	T getById(K k);
}
