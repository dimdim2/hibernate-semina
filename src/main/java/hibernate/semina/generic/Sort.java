package hibernate.semina.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.util.StringUtils;

public class Sort implements Iterable<Sort.Order> {

	public static final Direction DEFAULT_DIRECTION = Direction.ASC;

	private List<Order> orders = new ArrayList<Sort.Order>();

	public Sort(Order... orders) {
		this(Arrays.asList(orders));
	}

	public Sort(List<Order> orders) {
		this.orders = orders;
	}

	public Sort(String... properties) {
		this(DEFAULT_DIRECTION, properties);
	}

	public Sort(Direction direction, String... properties) {
		this(direction, Arrays.asList(properties));
	}

	public Sort(Direction direction, List<String> properties) {
		this.orders = new ArrayList<Order>(properties.size());
		for (String property : properties) {
			this.orders.add(new Order(direction, property));
		}
	}

	public Order getOrderFor(String property) {
		for (Order order : this.orders) {
			if (order.getProperty().equals(property)) {
				return order;
			}
		}

		return null;
	}

	@Override
	public String toString() {
		return StringUtils.collectionToCommaDelimitedString(orders);
	}

	public static enum Direction {

		ASC, DESC;

		public static Direction fromString(String value) {
			return Direction.valueOf(value.toUpperCase(Locale.US));
		}
	}

	public static class Order {

		private final Direction direction;
		private final String property;

		public Order(Direction direction, String property) {

			if (property == null || "".equals(property.trim())) {
				throw new IllegalArgumentException(
						"Property must not null or empty!");
			}

			this.direction = direction == null ? DEFAULT_DIRECTION : direction;
			this.property = property;
		}

		public Order(String property) {

			this(DEFAULT_DIRECTION, property);
		}

		public static List<Order> create(Direction direction,
				Iterable<String> properties) {

			List<Order> orders = new ArrayList<Sort.Order>();
			for (String property : properties) {
				orders.add(new Order(direction, property));
			}
			return orders;
		}

		public Direction getDirection() {

			return direction;
		}

		public String getProperty() {

			return property;
		}


		public boolean isAscending() {

			return this.direction.equals(Direction.ASC);
		}

		public Order with(Direction order) {

			return new Order(order, this.property);
		}


		public Sort withProperties(String... properties) {

			return new Sort(this.direction, properties);
		}

		@Override
		public String toString() {
			return String.format("%s: %s", property, direction);
		}
	}

	@Override
	public Iterator<Sort.Order> iterator() {
		return orders.iterator();
	}

	public List<Order> getOrders() {
		return orders;
	}

}
