package hackernews;

import java.io.IOException;

public class HackerNews{

	public static void main (String args[]){

		int posts = 0;

		if (args.length>0){
			if(args.length==1) {
			try {
				posts = Integer.parseInt(args[0]); //get the number of posts from the input

				if(posts>0 && posts<=100){
					try {
						System.out.println(Scraper.getInstance().scrape(posts)); //call the scraper class 
					} catch (IOException e) {
						System.err.println("IOException - "+e.getMessage());
						System.exit(1);
					}
				}else {
					System.err.println("Argument " + args[0] + " must be a positive integer <=100.");
					System.exit(1);
				}

			}catch (NumberFormatException e){
				System.err.println("Argument " + args[0] + " must be an integer.");
				System.exit(1);
			}catch (Exception e){
				System.err.println("Something went wrong - "+e.getMessage());
				System.exit(1);
			}	
			}else{
				System.err.println("Too many arguments");
				System.exit(1);
			}
		}else{
			System.err.println("Required argument: number of posts to print");
			System.exit(1);
		}
	}
}

