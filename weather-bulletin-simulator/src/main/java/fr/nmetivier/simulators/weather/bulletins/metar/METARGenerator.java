package fr.nmetivier.simulators.weather.bulletins.metar;

import java.time.LocalDateTime;

import fr.nmetivier.simulators.weather.bulletins.WeatherReportGenerator;

public class METARGenerator implements WeatherReportGenerator<Metar> {

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
				.runway(19, "L", 1500, 2500, "U")
				.build();

		final TemperatureGroup temperatureGroup = new TemperatureGroup.Builder()
				.temperature(32)
				.drewPoint(-10)
				.build();

		final QnhGroup qnhGroup = new QnhGroup.Builder()
				.pressure(1010)
				.build();

		return new Metar(identificationGroup, windGroup, visibilityGroup , temperatureGroup, qnhGroup);
	}

}
