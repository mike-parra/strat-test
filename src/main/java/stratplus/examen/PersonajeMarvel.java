package stratplus.examen;

import java.util.List;

public record PersonajeMarvel(int id, String name, String description, String modified, Thumbnail thumbnail,
		String resourceURI, Comics comics, Series series, Stories stories, Events events, List<Url> urls) {
	public static record Thumbnail(String path, String extension) {
	}

	public static record Comics(int available, String collectionURI, List<ComicSummary> items, int returned) {
		public static record ComicSummary(String resourceURI, String name) {
		}
	}

	public static record Series(int available, String collectionURI, List<SeriesSummary> items, int returned) {
		public static record SeriesSummary(String resourceURI, String name) {
		}
	}

	public static record Stories(int available, String collectionURI, List<StorySummary> items, int returned) {
		public static record StorySummary(String resourceURI, String name, String type) {
		}
	}

	public static record Events(int available, String collectionURI, List<EventSummary> items, int returned) {
		public static record EventSummary(String resourceURI, String name) {
		}
	}

	public static record Url(String type, String url) {
	}
}