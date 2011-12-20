package hibernate.semina.generic;

import java.io.Serializable;


public interface Model<ID> extends Serializable {

	ID getId();

}
