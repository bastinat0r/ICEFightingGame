import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class ReadSaveData {
	
	public static void saveMatrixData(String ownChar, String enemyChar, MatrixValue[][][] Matrix) throws Exception 
	{		
		Writer fw = null;
		fw = new FileWriter( ownChar+"_"+enemyChar+".txt" );
		try
		{
			for ( int state = 0; state < 4096; state++ )
			{
				for ( int enemyState = 0 ; enemyState < 5; enemyState++)
				{
					for ( int action = 0; action < 20; action++ )
					{
						if (Matrix[state][enemyState][action] == null){
							fw.write( "0.0|0" );
						}
						else{
							fw.write( Float.toString(Matrix[state][enemyState][action].getReward()) );
							fw.write( "|" );
							fw.write( Integer.toString(Matrix[state][enemyState][action].getVisitors()) );
						}
						fw.append( System.getProperty("line.separator") ); // e.g. "\n"
					}
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
	
	public static MatrixValue[][][] readMatrixData (String ownChar, String enemyChar) throws Exception {
		MatrixValue[][][] Matrix = new MatrixValue[4096][5][20];
		FileReader fr = new FileReader(ownChar+"_"+enemyChar+".txt");
	    BufferedReader br = new BufferedReader(fr);
	    String line = "";
	    int splitPosition;
    	for ( int state = 0; state < 4096; state++ )
		{
			for ( int enemyState = 0 ; enemyState < 5; enemyState++)
			{
				for ( int action = 0; action < 20; action++ )
				{
					line = br.readLine();
					splitPosition = line.indexOf("|");
					Matrix[state][enemyState][action] = new MatrixValue(Float.parseFloat(line.substring(0, splitPosition)),Integer.parseInt(line.substring(splitPosition+1)));					
				}
			}
		}
	    br.close();
		return Matrix;
	}
}
