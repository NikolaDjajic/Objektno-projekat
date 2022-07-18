import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

				String datum1S = "2020-02-01";
				String datum2S = "2020-06-01";

				// parsing the string date into LocalDate objects.
				LocalDate datum1 = LocalDate.parse(datum1S);
				LocalDate datum2 = LocalDate.parse(datum2S);
				long razlika= ChronoUnit.DAYS.between(datum1, datum2);
				
				System.out.println(razlika);
	}

}
