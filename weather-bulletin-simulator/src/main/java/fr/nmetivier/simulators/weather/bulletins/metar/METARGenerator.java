package fr.nmetivier.simulators.weather.bulletins.metar;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import fr.nmetivier.simulators.weather.bulletins.WeatherReportGenerator;

public class METARGenerator implements WeatherReportGenerator<Metar> {

	private boolean mustGenerate = false;
	private final Timer timer = new Timer();

	@Override
	public Metar generate() {
		final IdentificationGroup identificationGroup = new IdentificationGroup.Builder()
				.icao("LFBO")
				.date(LocalDateTime.of(2023, 2, 7, 22, 30))
				.automatic(false)
				.build();

		final WindGroup windGroup = new WindGroup.Builder()
				.direction(200, 210, 230)
				.wind(5)
				.gust(10)
				.build();

		final VisibilityGroup visibilityGroup = new VisibilityGroup.Builder()
				.minimumHorizontalVisibility(6000)
				.north(2540)
				.runway(19, "L", 1500, 2500, Tendency.DOWN)
				.build();

		final CurrentWeatherGroup currentWeatherGroup = new CurrentWeatherGroup.Builder()
				.rain(true)
				.build();

		final CloudinessGroup cloudinessGroup = new CloudinessGroup.Builder()
				.cloudCover(150, CloudDensity.FEW, "///")
				// .noSignificativeCloud()
				.build();
	
		final TemperatureGroup temperatureGroup = new TemperatureGroup.Builder()
				.temperature(32)
				.drewPoint(-10)
				.build();

		final QnhGroup qnhGroup = new QnhGroup.Builder()
				.pressure(1010)
				.build();

		return new Metar(identificationGroup, windGroup, visibilityGroup , currentWeatherGroup, cloudinessGroup, temperatureGroup, qnhGroup);
	}

	@Override
	public void generate(int internalInMilliseconds, Consumer<Metar> callback) {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				final Metar metar = generate();
				callback.accept(metar);
			}
		};
	  timer.scheduleAtFixedRate(task, 0, internalInMilliseconds);
	}
	
	@Override
	public void stop() {
		timer.cancel();
	}

}
