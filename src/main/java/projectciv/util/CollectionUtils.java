package main.java.projectciv.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionUtils {
	public static <E> ArrayList<E> copyList(List<E> list) {
		return new ArrayList<E>(list);
	}
	
	public static <E> List<E> unmodifiableCopyList(List<E> list) {
		return unmodifiableList(copyList(list));
	}
	
	public static <E> List<E> unmodifiableList(List<E> list) {
		return Collections.unmodifiableList(list);
	}
}
