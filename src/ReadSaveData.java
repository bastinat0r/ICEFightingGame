import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class ReadSaveData {
	
	public static void saveMatrixData(String ownChar, String enemyChar, MatrixValue[][] Matrix) throws Exception 
	{		
		Writer fw;
		fw = new FileWriter( "./data/aiData/" + ownChar+"_"+enemyChar+".txt" );
		try
		{
			for ( int state = 0; state < 1 << 14; state++ ) //jede Kombination der boolschen Zust�nde
			{
				for ( int action = 0; action < 20; action++ ) //jede m�gliche Spieleraktion
				{
					if (Matrix[state][action] == null) //Initiale Anlage
					{
						fw.write( "0.0|0" );
					}
					else{
						fw.write( Float.toString(Matrix[state][action].getReward()) );
						fw.write( "|" );
						fw.write( Integer.toString(Matrix[state][action].getVisitors()) );
					}
					fw.append( System.getProperty("line.separator") ); // e.g. "\n"
				}

			}
		}
		catch ( IOException e ) {
		  System.err.println( "Konnte Datei nicht erstellen" );
		}
		finally {
		  if ( fw != null )
		    try { fw.close(); } catch ( IOException e ) { e.printStackTrace(); }
		}
	}
	
	public static MatrixValue[][] readMatrixData (String ownChar, String enemyChar){
		
		MatrixValue[][] Matrix = new MatrixValue[1 << 14][20];
		for ( int state = 0; state < 1<<14; state++ )
		{
			for ( int action = 0; action < 20; action++ )
			{
				Matrix[state][action] = new MatrixValue(0,0);					
			}
		}
		try {
			FileReader fr = new FileReader("./data/aiData/" + ownChar+"_"+enemyChar+".txt");
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			int splitPosition;
			for ( int state = 0; state < 1<<14; state++ )
			{
				for ( int action = 0; action < 20; action++ )
				{
					line = br.readLine();
					splitPosition = line.indexOf("|");
					Matrix[state][action] = new MatrixValue(Float.parseFloat(line.substring(0, splitPosition)),Integer.parseInt(line.substring(splitPosition+1)));					
				}

			}
			br.close();
		}
		catch (IOException e) {
			System.out.println("Could not read file, starting with 0.0|0");	
		}
		return Matrix;
	}
}
