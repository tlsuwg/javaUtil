/**
 * 
 */
package com.example.com.suyi.domain;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Map;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;

/**
 * @author "suwg"
 * 2016Äê5ÔÂ30ÈÕ
 */
public class SuMePlayer extends MediaPlayer {

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setDisplay(android.view.SurfaceHolder)
	 */
	@Override
	public void setDisplay(SurfaceHolder sh) {
		// TODO Auto-generated method stub
		super.setDisplay(sh);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setSurface(android.view.Surface)
	 */
	@Override
	public void setSurface(Surface surface) {
		// TODO Auto-generated method stub
		super.setSurface(surface);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setVideoScalingMode(int)
	 */
	@Override
	public void setVideoScalingMode(int mode) {
		// TODO Auto-generated method stub
		super.setVideoScalingMode(mode);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setDataSource(android.content.Context, android.net.Uri)
	 */
	@Override
	public void setDataSource(Context context, Uri uri) throws IOException,
			IllegalArgumentException, SecurityException, IllegalStateException {
		// TODO Auto-generated method stub
		super.setDataSource(context, uri);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setDataSource(android.content.Context, android.net.Uri, java.util.Map)
	 */
	@Override
	public void setDataSource(Context context, Uri uri,
			Map<String, String> headers) throws IOException,
			IllegalArgumentException, SecurityException, IllegalStateException {
		// TODO Auto-generated method stub
		super.setDataSource(context, uri, headers);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setDataSource(java.lang.String)
	 */
	@Override
	public void setDataSource(String path) throws IOException,
			IllegalArgumentException, SecurityException, IllegalStateException {
		// TODO Auto-generated method stub
		super.setDataSource(path);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setDataSource(java.io.FileDescriptor)
	 */
	@Override
	public void setDataSource(FileDescriptor fd) throws IOException,
			IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		super.setDataSource(fd);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setDataSource(java.io.FileDescriptor, long, long)
	 */
	@Override
	public void setDataSource(FileDescriptor fd, long offset, long length)
			throws IOException, IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		super.setDataSource(fd, offset, length);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#prepare()
	 */
	@Override
	public void prepare() throws IOException, IllegalStateException {
		// TODO Auto-generated method stub
		super.prepare();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#prepareAsync()
	 */
	@Override
	public void prepareAsync() throws IllegalStateException {
		// TODO Auto-generated method stub
		super.prepareAsync();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#start()
	 */
	@Override
	public void start() throws IllegalStateException {
		// TODO Auto-generated method stub
		super.start();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#stop()
	 */
	@Override
	public void stop() throws IllegalStateException {
		// TODO Auto-generated method stub
		super.stop();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#pause()
	 */
	@Override
	public void pause() throws IllegalStateException {
		// TODO Auto-generated method stub
		super.pause();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setWakeMode(android.content.Context, int)
	 */
	@Override
	public void setWakeMode(Context context, int mode) {
		// TODO Auto-generated method stub
		super.setWakeMode(context, mode);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setScreenOnWhilePlaying(boolean)
	 */
	@Override
	public void setScreenOnWhilePlaying(boolean screenOn) {
		// TODO Auto-generated method stub
		super.setScreenOnWhilePlaying(screenOn);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#getVideoWidth()
	 */
	@Override
	public int getVideoWidth() {
		// TODO Auto-generated method stub
		return super.getVideoWidth();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#getVideoHeight()
	 */
	@Override
	public int getVideoHeight() {
		// TODO Auto-generated method stub
		return super.getVideoHeight();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#isPlaying()
	 */
	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return super.isPlaying();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#seekTo(int)
	 */
	@Override
	public void seekTo(int msec) throws IllegalStateException {
		// TODO Auto-generated method stub
		super.seekTo(msec);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#getCurrentPosition()
	 */
	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return super.getCurrentPosition();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#getDuration()
	 */
	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return super.getDuration();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setNextMediaPlayer(android.media.MediaPlayer)
	 */
	@Override
	public void setNextMediaPlayer(MediaPlayer next) {
		// TODO Auto-generated method stub
		super.setNextMediaPlayer(next);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#release()
	 */
	@Override
	public void release() {
		// TODO Auto-generated method stub
		super.release();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#reset()
	 */
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		super.reset();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setAudioStreamType(int)
	 */
	@Override
	public void setAudioStreamType(int streamtype) {
		// TODO Auto-generated method stub
		super.setAudioStreamType(streamtype);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setLooping(boolean)
	 */
	@Override
	public void setLooping(boolean looping) {
		// TODO Auto-generated method stub
		super.setLooping(looping);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#isLooping()
	 */
	@Override
	public boolean isLooping() {
		// TODO Auto-generated method stub
		return super.isLooping();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setVolume(float, float)
	 */
	@Override
	public void setVolume(float leftVolume, float rightVolume) {
		// TODO Auto-generated method stub
		super.setVolume(leftVolume, rightVolume);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setAudioSessionId(int)
	 */
	@Override
	public void setAudioSessionId(int sessionId)
			throws IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		super.setAudioSessionId(sessionId);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#getAudioSessionId()
	 */
	@Override
	public int getAudioSessionId() {
		// TODO Auto-generated method stub
		return super.getAudioSessionId();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#attachAuxEffect(int)
	 */
	@Override
	public void attachAuxEffect(int effectId) {
		// TODO Auto-generated method stub
		super.attachAuxEffect(effectId);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setAuxEffectSendLevel(float)
	 */
	@Override
	public void setAuxEffectSendLevel(float level) {
		// TODO Auto-generated method stub
		super.setAuxEffectSendLevel(level);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#getTrackInfo()
	 */
	@Override
	public TrackInfo[] getTrackInfo() throws IllegalStateException {
		// TODO Auto-generated method stub
		return super.getTrackInfo();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#addTimedTextSource(java.lang.String, java.lang.String)
	 */
	@Override
	public void addTimedTextSource(String path, String mimeType)
			throws IOException, IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		super.addTimedTextSource(path, mimeType);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#addTimedTextSource(android.content.Context, android.net.Uri, java.lang.String)
	 */
	@Override
	public void addTimedTextSource(Context context, Uri uri, String mimeType)
			throws IOException, IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		super.addTimedTextSource(context, uri, mimeType);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#addTimedTextSource(java.io.FileDescriptor, java.lang.String)
	 */
	@Override
	public void addTimedTextSource(FileDescriptor fd, String mimeType)
			throws IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		super.addTimedTextSource(fd, mimeType);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#addTimedTextSource(java.io.FileDescriptor, long, long, java.lang.String)
	 */
	@Override
	public void addTimedTextSource(FileDescriptor fd, long offset, long length,
			String mime) throws IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		super.addTimedTextSource(fd, offset, length, mime);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#selectTrack(int)
	 */
	@Override
	public void selectTrack(int index) throws IllegalStateException {
		// TODO Auto-generated method stub
		super.selectTrack(index);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#deselectTrack(int)
	 */
	@Override
	public void deselectTrack(int index) throws IllegalStateException {
		// TODO Auto-generated method stub
		super.deselectTrack(index);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#finalize()
	 */
	@Override
	protected void finalize() {
		// TODO Auto-generated method stub
		super.finalize();
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setOnPreparedListener(android.media.MediaPlayer.OnPreparedListener)
	 */
	@Override
	public void setOnPreparedListener(OnPreparedListener listener) {
		// TODO Auto-generated method stub
		super.setOnPreparedListener(listener);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setOnCompletionListener(android.media.MediaPlayer.OnCompletionListener)
	 */
	@Override
	public void setOnCompletionListener(OnCompletionListener listener) {
		// TODO Auto-generated method stub
		super.setOnCompletionListener(listener);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setOnBufferingUpdateListener(android.media.MediaPlayer.OnBufferingUpdateListener)
	 */
	@Override
	public void setOnBufferingUpdateListener(OnBufferingUpdateListener listener) {
		// TODO Auto-generated method stub
		super.setOnBufferingUpdateListener(listener);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setOnSeekCompleteListener(android.media.MediaPlayer.OnSeekCompleteListener)
	 */
	@Override
	public void setOnSeekCompleteListener(OnSeekCompleteListener listener) {
		// TODO Auto-generated method stub
		super.setOnSeekCompleteListener(listener);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setOnVideoSizeChangedListener(android.media.MediaPlayer.OnVideoSizeChangedListener)
	 */
	@Override
	public void setOnVideoSizeChangedListener(
			OnVideoSizeChangedListener listener) {
		// TODO Auto-generated method stub
		super.setOnVideoSizeChangedListener(listener);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setOnTimedTextListener(android.media.MediaPlayer.OnTimedTextListener)
	 */
	@Override
	public void setOnTimedTextListener(OnTimedTextListener listener) {
		// TODO Auto-generated method stub
		super.setOnTimedTextListener(listener);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setOnErrorListener(android.media.MediaPlayer.OnErrorListener)
	 */
	@Override
	public void setOnErrorListener(OnErrorListener listener) {
		// TODO Auto-generated method stub
		super.setOnErrorListener(listener);
	}

	/* (non-Javadoc)
	 * @see android.media.MediaPlayer#setOnInfoListener(android.media.MediaPlayer.OnInfoListener)
	 */
	@Override
	public void setOnInfoListener(OnInfoListener listener) {
		// TODO Auto-generated method stub
		super.setOnInfoListener(listener);
	}
	
	

}
