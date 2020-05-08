package hendrixjoseph;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

public class FundVenn {
	public static void main(String... strings) {

		var vanguardVenn = new FundVenn("data/");
		var sets = vanguardVenn.compute();

		sets.stream()
		.sorted((fund1, fund2) -> fund1.name.compareTo(fund2.name))
		.sorted((fund1, fund2) -> fund1.name.length() - fund2.name.length())
		.forEach(System.out::println);
	}

	Set<Fund> funds = new HashSet<>();

	public FundVenn(String dataDir) {
		this(new File(dataDir));
	}

	public FundVenn(File dataDir)  {
		funds.addAll(readFiles(dataDir.listFiles()));
	}

	public Set<Fund> readFiles(File[] files) {
		return Arrays.stream(files).map(Fund::new).collect(Collectors.toSet());
	}

	public Set<Fund> compute() {
		Set<Set<Fund>> combos = Sets.powerSet(funds);

		return combos.stream().filter(funds -> funds.size() > 0).map(Fund::new).collect(Collectors.toSet());
	}
}
