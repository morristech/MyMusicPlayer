package org.app.netmusic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.app.music.bean.Album;
import org.app.music.bean.Artist;
import org.app.music.bean.Song;
import org.app.musicplayer.R;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.XmlResourceParser;

public class XmlParse {
	/**
	 * 解析网络音乐列表的xml,因为没有相应的API所以自己解析+下载。
	 */
	public static List<Song> parseWebSongList(Context context) {
		List<Song> data = null;
		Song song = null;
		XmlResourceParser parser = context.getResources()
				.getXml(R.xml.web_list);
		try {
			int eventType = parser.getEventType();
			// 一直读到文档结束
			while (eventType != XmlResourceParser.END_DOCUMENT) {
				switch (eventType) {
				// 开始读取文档
				case XmlResourceParser.START_DOCUMENT:
					data = new ArrayList<Song>();
					break;
				// 开始读取文档标签
				case XmlResourceParser.START_TAG:
					if (parser.getName().equals("song")) {
						song = new Song();
						song.setId(parser.getAttributeIntValue(0, 0));
					} else if (parser.getName().equals("name")) {
						song.setName(parser.nextText());
					} else if (parser.getName().equals("artist")) {
						song.setArtist(new Artist(0, parser.nextText(), null));
					} else if (parser.getName().equals("album")) {
						song.setAlbum(new Album(0, parser.nextText(), null));
					} else if (parser.getName().equals("displayName")) {
						song.setDisplayName(parser.nextText());
					} else if (parser.getName().equals("mimeType")) {
						song.setMimeType(parser.nextText());
					} else if (parser.getName().equals("netUrl")) {
						song.setNetUrl(parser.nextText());
					} else if (parser.getName().equals("durationTime")) {
						song.setDurationTime(Integer.valueOf(parser.nextText()));
					} else if (parser.getName().equals("size")) {
						song.setSize(Integer.valueOf(parser.nextText()));
					}
					break;
				// 读取文档标签结束
				case XmlResourceParser.END_TAG:
					if (parser.getName().equals("song")) {
						data.add(song);
						song = null;
					}
					break;
				default:
					break;
				}
				// 读取下个元素
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;

	}
}