package synereo.client.services

import autowire._
import org.scalajs.dom._
import shared.Api
import shared.models._
import shared.dtos._
import synereo.client.models.{EmailValidationModel}
import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import upickle.default._
import boopickle.Default._
import scala.concurrent.Future
import synereo.client.utils._

/**
  * Created by shubham.k on 12/14/2015.
  */
object CoreApi {
  val CREATE_USER_REQUEST_MSG = "createUserRequest"
  val CONFIRM_EMAIL_MSG = "confirmEmailToken"
  val INITIALIZE_SESSION_MSG = "initializeSessionRequest"
  val SESSION_PING = "sessionPing"
  val PROJECT_MSG = "projectsRequest"
  val EVAL_SUBS_REQUEST = "evalSubscribeRequest"
  val EVAL_SUBS_CANCEL_REQUEST = "evalSubscribeCancelRequest"
  val MESSAGES_SESSION_URI = "messagesSessionUri"
  val CONNECTIONS_SESSION_URI = "connectionsSessionUri"
  //  var BASE_URL = "http://52.35.10.219:9876/api"
  //  var CREATE_USER_REQUEST_MSG = "createUserRequest"
  //  private def ajaxPost(msgType: String, data: RequestContent): Future[String] = {
  //    Ajax.post(
  //      url = BASE_URL,
  //      data = write(ApiRequest(msgType, data)),
  //      headers = Map("Content-Type" -> "application/json;charset=UTF-8")
  //    ).map(_.responseText)
  //  }

  def createUser(userModel: UserModel): Future[ /*ApiResponse[CreateUserResponse]*/ String] = {
    val requestContent = upickle.default.write(ApiRequest(CREATE_USER_REQUEST_MSG, CreateUser(userModel.email, userModel.password,
      Map("name" -> userModel.name), true)))
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }

  def emailValidation(emailValidationModel: EmailValidationModel): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(CONFIRM_EMAIL_MSG, ConfirmEmail(emailValidationModel.token)))
    println("emailvalidation token : " + requestContent)
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }

  def agentLogin(userModel: UserModel): Future[ /*ApiResponse[InitializeSessionResponse]*/ String] = {
    val requestContent = upickle.default.write(ApiRequest(INITIALIZE_SESSION_MSG, InitializeSession(s"agent://email/${userModel.email}" +
      s"?password=${userModel.password}")))
    println("login data : " + requestContent)
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }

  def sessionPing(uri: String): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(SESSION_PING, SessionPing(window.sessionStorage.getItem(uri))))
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }

  def getConnections(): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(SESSION_PING, SessionPing(window.sessionStorage.getItem(CONNECTIONS_SESSION_URI))))
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }

  def getMessages(): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(SESSION_PING, SessionPing(window.sessionStorage.getItem(MESSAGES_SESSION_URI))))
    val currentLabels = window.sessionStorage.getItem("currentSearchLabel")
    val previousLabels = window.sessionStorage.getItem("previousSearchLabel")
    val selfConnection = Utils.GetSelfConnnection(MESSAGES_SESSION_URI)
    val getMessagesSubscription = SubscribeRequest(window.sessionStorage.getItem(MESSAGES_SESSION_URI), Expression(msgType = "feedExpr", ExpressionContent(Seq(selfConnection), currentLabels)))
    val cancelPreviousRequest = CancelSubscribeRequest(window.sessionStorage.getItem(MESSAGES_SESSION_URI), Seq(selfConnection), previousLabels)
    //    todo move the logic from button click messages subscription to messages feed
    val messageSearchClick = window.sessionStorage.getItem("messageSearchClick")
    if (messageSearchClick != null) {
      window.sessionStorage.setItem("previousSearchLabel", currentLabels)
      for {
        cancel <- cancelSubscriptionRequest(cancelPreviousRequest)
        newSubscription <- evalSubscribeRequest(getMessagesSubscription)
        messages <- AjaxClient[Api].queryApiBackend(requestContent).call()
      } yield messages
    } else {
      window.sessionStorage.setItem("messageSearchClick", "true")
      window.sessionStorage.setItem("previousSearchLabel", currentLabels)
      for {
      //        cancel <- cancelSubscriptionRequest(cancelPreviousRequest)
        newSubscription <- evalSubscribeRequest(getMessagesSubscription)
        messages <- AjaxClient[Api].queryApiBackend(requestContent).call()
      } yield messages
    }


  }

  def getProjects(): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(PROJECT_MSG, SessionPing(window.sessionStorage.getItem("sessionURI"))))
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }

  def evalSubscribeRequest(subscribeRequest: SubscribeRequest): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(EVAL_SUBS_REQUEST, subscribeRequest))
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }

  def cancelSubscriptionRequest(cancelSubscribeRequest: CancelSubscribeRequest): Future[String] = {
    val requestContent = upickle.default.write(ApiRequest(EVAL_SUBS_CANCEL_REQUEST, cancelSubscribeRequest))
    AjaxClient[Api].queryApiBackend(requestContent).call()
  }

}