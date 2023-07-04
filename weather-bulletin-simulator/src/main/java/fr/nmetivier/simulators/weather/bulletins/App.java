package fr.nmetivier.simulators.weather.bulletins;

import fr.nmetivier.simulators.weather.bulletins.metar.METARGenerator;
import fr.nmetivier.simulators.weather.bulletins.metar.Metar;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        // VISIBILITY GROUP
        // 6000 R20L/0300D or R20L/0150V0300U
        //   |----------------> 6000 metres (9999 si > 10km)
        // 6000NE1500 
        //   |----------------> visi majoritiare à 6000 metres mais 1500 metres pour secteur Nord est (N, E, S, W)
        // R20L -> piste numéro 20
        // 0300D -> visibilité de la piste gauche est de 300 mètres et en baisse (D=baisse, U=hausse, N=sans changement)

        // CURRENT WEATHER GROUP
        // RA or CAVOK
        // ra=pluis, sn=neige
        // CAVOK = visi > 10km+ pas de nuage < 5000 pieds + pas de CB et TCU + pas de précipitation ou averses

        // NEBULOSITY GROUP 
        // FEW005/// BKN030CB ///CB
        // FEW à 500 pied
        // BROKEN à 3000 pied
        // FEW, SCT, BKN, OVC XXX
        // NSC
        // 

        // Temperature GROUP
        // 10/M05
        // 10 = degres celsus
        // 05 = temp point de rosé 5 egres celsucs
        // M = négatif

        // OTHER GROUP
        // TEMPO FM1400 TS1430 OVC015

        WeatherReportGenerator<Metar> generator = new METARGenerator();
        Metar metar = generator.generate();
        System.out.println(metar);
    }
}
