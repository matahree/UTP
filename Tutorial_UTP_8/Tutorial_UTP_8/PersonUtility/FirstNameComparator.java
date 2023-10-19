package PersonUtility;

import java.util.Comparator;


public class FirstNameComparator implements Comparator<Person> {

	@Override
	public int compare(Person person1, Person person2) {
		return person1.getFirstName().compareToIgnoreCase(person2.getFirstName());
	}
}