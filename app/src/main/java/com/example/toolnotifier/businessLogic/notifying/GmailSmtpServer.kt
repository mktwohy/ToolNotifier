package com.example.toolnotifier.businessLogic.notifying

import com.example.toolnotifier.util.Log
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.util.*
import javax.activation.DataHandler
import javax.activation.DataSource
import javax.activation.FileDataSource
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage

// based off of https://gist.github.com/oussemaAr/50b6189955f6036ebfdb0bacdc433bf4

internal class GmailSmtpServer(
    private val user: String,
    private val password: String
) : Authenticator() {
    private val mailhost = "smtp.gmail.com"
    private val session: Session

    init {
        val props = Properties().apply {
            setProperty("mail.transport.protocol", "smtp")
            setProperty("mail.host", mailhost)
            setProperty("mail.smtp.auth", "true")
            setProperty("mail.smtp.port", "465")
            setProperty("mail.smtp.socketFactory.port", "465")
            setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            setProperty("mail.smtp.socketFactory.fallback", "false")
            setProperty("mail.smtp.quitwait", "false")
        }

        session = Session.getDefaultInstance(props, this)
    }

    override fun getPasswordAuthentication(): PasswordAuthentication {
        return PasswordAuthentication(user, password)
    }

    @Synchronized
    fun sendMail(
        subject: String?,
        body: String,
        sender: String?,
        recipients: List<String>
    ) {
        try {
            val dataSource = ByteArrayDataSource(body.toByteArray(), "text/plain")
            val handler = DataHandler(dataSource)
            val message = MimeMessage(session).apply {
                sender?.let { this.sender = InternetAddress(it) }
                subject?.let { this.subject = it }
                this.dataHandler = handler
                this.setText(body)

                if (recipients.size > 1) {
                    this.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(recipients.joinToString(separator = ","))
                    )
                } else {
                    this.setRecipient(
                        Message.RecipientType.TO,
                        InternetAddress(recipients.first())
                    )
                }
            }
            Transport.send(message)
            Log.i(
                """
                    Message Sent Successfully!
                    - recipients: $recipients
                    - sender: $sender
                    - subject: $subject
                    - body: $body
                """.trimIndent()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun Multipart.addAttachment(filename: String?) {
        val messageBodyPart: BodyPart = MimeBodyPart()
        val source: DataSource = FileDataSource(filename)
        messageBodyPart.dataHandler = DataHandler(source)
        messageBodyPart.fileName = "download image"
        this.addBodyPart(messageBodyPart)
    }

    inner class ByteArrayDataSource(
        private var data: ByteArray,
        private var type: String? = null
    ) : DataSource {
        override fun getContentType(): String {
            return type ?: "application/octet-stream"
        }

        override fun getInputStream(): InputStream {
            return ByteArrayInputStream(data)
        }

        override fun getName(): String {
            return "ByteArrayDataSource"
        }

        override fun getOutputStream(): OutputStream {
            throw IOException("Not Supported")
        }
    }
}
