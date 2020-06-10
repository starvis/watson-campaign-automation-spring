package com.github.ka4ok85.wca.sftp;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.exceptions.EngageSftpException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.pod.Pod;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTP {
	private static final int port = 22;
	private static final String username = "oauth";
	private static final String downloadDirectory = "download";
	private static final String uploadDirectory = "upload";
	private static final Logger log = LoggerFactory.getLogger(SFTP.class);

	private OAuthClient oAuthClient;

	public SFTP(OAuthClient oAuthClient) {
		this.oAuthClient = oAuthClient;
	}

	public void download(String filePath, String localAbsoluteFilePath) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(username, Pod.getSFTPHostName(this.oAuthClient.getPodIdentifier()), port);
			session.setPassword(this.oAuthClient.getAccessToken());
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			log.debug("Connecting to SFTP. Hostname is {}", Pod.getSFTPHostName(this.oAuthClient.getPodIdentifier()));
			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp channelSftp = (ChannelSftp) channel;

			log.debug("Changing SFTP directory to {}", downloadDirectory);
			channelSftp.cd(downloadDirectory);

			log.debug("Downloading remote file {} into local file {}", filePath, localAbsoluteFilePath);
			channelSftp.get(filePath, localAbsoluteFilePath);

			log.debug("Disconnecting from SFTP");
			channelSftp.exit();
			session.disconnect();
		} catch (JSchException | SftpException e) {
			log.warn("SFTP Error is {}", e.getMessage());
			throw new EngageSftpException(e.getMessage());
		} catch (FailedGetAccessTokenException e) {
			log.warn("Can not get Access Token for SFTP. Error is {}", e.getMessage());
			throw new EngageSftpException(e.getMessage());
		}
	}

	public void upload(String localAbsoluteFilePath, String filePath) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(username, Pod.getSFTPHostName(this.oAuthClient.getPodIdentifier()), port);
			session.setPassword(this.oAuthClient.getAccessToken());
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			log.debug("Connecting to SFTP. Hostname is {}", Pod.getSFTPHostName(this.oAuthClient.getPodIdentifier()));
			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp channelSftp = (ChannelSftp) channel;

			log.debug("Changing SFTP directory to {}", uploadDirectory);
			channelSftp.cd(uploadDirectory);

			log.debug("Uploading local file {} into remote file {}", localAbsoluteFilePath, filePath);
			channelSftp.put(localAbsoluteFilePath, filePath);

			log.debug("Disconnecting from SFTP");
			channelSftp.exit();
			session.disconnect();
		} catch (JSchException | SftpException e) {
			log.warn("SFTP Error is {}", e.getMessage());
			throw new EngageSftpException(e.getMessage());
		} catch (FailedGetAccessTokenException e) {
			log.warn("Can not get Access Token for SFTP. Error is {}", e.getMessage());
			throw new EngageSftpException(e.getMessage());
		}
	}

}
