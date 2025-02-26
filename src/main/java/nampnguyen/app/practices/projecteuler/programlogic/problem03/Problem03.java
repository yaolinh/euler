package nampnguyen.app.practices.projecteuler.programlogic.problem03;

public class Problem03 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Uses pollard to find the number, but don't know why it stops at 71;
		Long number = 600851475143L, x_fixed = 2l, cycle_size = 2l, x = 2l, factor = 1l;
		while (factor == 1) {
			for (int count=1;count <= cycle_size && factor <= 1;count++) {
				x = (x*x+1)%number;
				System.out.println("X: " + x);
				factor = gcd(Math.abs(x - x_fixed), number);
				System.out.println("Factor: " + factor);
			}

			cycle_size *= 2;
			x_fixed = x;
		}
		//Continue to find the number;
		Long d = factor;
		Long n = number;
		while(n > 1){
			while(n % d == 0){
				factor = d;
				n /= d;
			}
			d+=1;
		}
		System.out.println("The largest prime factor is: " + factor);
	}

	static Long gcd(Long a, Long b){
		Long remainder = Long.MIN_VALUE;
		while(0 != b){
			remainder = a % b;
			a = b;
			b = remainder;
		}
		return a;
	}

}
