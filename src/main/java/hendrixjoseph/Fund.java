package hendrixjoseph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

public class Fund {
	Set<String> assets = new HashSet<>();
	String name;

	public Fund(Set<Fund> funds) {
		this(comboName(funds), intersectFunds(funds));
	}

	public Fund(File file) {
		this(trimFilename(file), readFile(file));
	}

	private static Set<String> intersectFunds(Set<Fund> funds) {
		return funds.stream().map(fund -> fund.assets).reduce((soFar, thisOne) -> Sets.intersection(soFar, thisOne)).get();
	}

	private static String comboName(Set<Fund> funds) {
		return funds.stream()
				.map(Fund::getName)
				.sorted((name1, name2) -> name1.compareTo(name2))
				.reduce((newName, name) -> newName + " ^ " + name)
				.get();
	}

	private static Set<String> readFile(File file) {
		try {
			return Files.readAllLines(file.toPath())
					.stream().filter(string -> !string.isEmpty())
					.map(string -> string.replaceAll(" Class .+$", ""))
					.collect(Collectors.toSet());
		} catch (IOException e) {
			e.printStackTrace();
			return Collections.emptySet();
		}
	}

	private static String trimFilename(File file) {
		return file.getName().replaceAll("\\.txt$", "").toUpperCase();
	}

	public Fund(String name, Set<String> assets) {
		this.name = name;
		this.assets.addAll(assets);
	}

	public int size() {
		return this.assets.size();
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name + " " + this.size();
	}
}
