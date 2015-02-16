package model;

import java.util.Random;

/**
 * Genere des tableaux de distances aleatoires
 * 
 * @authors Timothé Barbe, Florent Euvrard, Cheikh Sylla
 *
 */
public class GenerateDistances {

	public static int[][] genererTableau(int nbClubs, int distanceMax){
		int[][] tabSymetrique = new int[nbClubs][nbClubs];
		
		Random r = new Random(0);
		
		for(int i=0; i<nbClubs; i++){
			for(int j=i+1; j<nbClubs; j++){
				int a = r.nextInt(distanceMax);
				while(a == 0){
					a = r.nextInt(distanceMax);
				}
				tabSymetrique[i][j] = a;
			}
		}
		for(int i=0; i<nbClubs; i++){
			for(int j=i+1; j<nbClubs; j++){
				tabSymetrique[j][i] = tabSymetrique[i][j];
			}
		}
		
		return tabSymetrique;
	}
	
	public static String afficherMatrice(int[][] matrice){
		String s="";
		
		int j=0;
		while(j<matrice.length){
			for(int i=0; i<matrice.length; i++){
				s+=matrice[i][j]+" ";
			}
			s+="\n";
			j++;
		}
		
		return s;
	}
	
	public static void main(String[] args) {
		int[][] matriceSymetrique = genererTableau(10, 5);
		System.out.println(afficherMatrice(matriceSymetrique));
	}
}
