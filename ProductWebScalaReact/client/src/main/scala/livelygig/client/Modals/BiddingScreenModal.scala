package livelygig.client.modals

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.OnUnmount
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import livelygig.client.LGMain.Loc
import livelygig.client.components.Bootstrap._
import livelygig.client.components._
import livelygig.client.css._
import livelygig.client.logger._
import livelygig.client.models.UserModel
import livelygig.client.services.CoreApi._
import livelygig.client.services._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalacss.ScalaCssReact._

//object BiddingScreenModal {
//  @inline private def bss = GlobalStyles.bootstrapStyles
//
//  case class Props(ctl: RouterCtl[Loc])
//  case class State(showBiddingScreen: Boolean = false)
//  abstract class RxObserver[BS <: BackendScope[_, _]](scope: BS) extends OnUnmount {
//  }
//
//  class Backend(t: BackendScope[Props, State]) extends RxObserver(t) {
//    def mounted(props: Props): Callback = {
//      t.modState(s => s.copy(showBiddingScreen = true))
//    }
//    def addBiddingScreenForm(): Callback = {
//      t.modState(s => s.copy(showBiddingScreen = true))
//    }
//    def addBiddingScreen(postBiddingScreen: Boolean = false): Callback = {
////      log.debug(s"addNewAgent userModel : ${userModel} ,addNewAgent: ${showBiddingScreen}")
//      if (postBiddingScreen) {
//        t.modState(s => s.copy(showBiddingScreen = true))
//      } else {
//        t.modState(s => s.copy(showBiddingScreen = false))
//      }
//    }
//  }
//
//  val component = ReactComponentB[Props]("AddNewAgent")
//    .initialState(State())
//    .backend(new Backend(_))
//    .renderPS(($, P, S) => {
//      val B = $.backend
//      <.div(ProjectCSS.Style.displayInitialbtn)(
//        Button(Button.Props(B.addBiddingScreenForm(), CommonStyle.default, Seq(HeaderCSS.Style.createNewProjectBtn)), "View/Edit Contract"),
//        if (S.showBiddingScreen) BiddingScreenModalForm(BiddingScreenModalForm.Props(B.addBiddingScreen))
//        else
//          Seq.empty[ReactElement]
//      )
//    })
//    //  .componentDidMount(scope => scope.backend.mounted(scope.props))
//    .configure(OnUnmount.install)
//    .build
//
//  def apply(props: Props) = component(props)
//}

object BiddingScreenModalForm {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles
  case class Props(submitHandler: (Boolean) => Callback)
  case class State(postBiddingScreen: Boolean = false)


  case class Backend(t: BackendScope[Props, State]) /* extends RxObserver(t)*/ {
    def hide = Callback {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }
    def hidemodal =  {
      // instruct Bootstrap to hide the modal
      jQuery(t.getDOMNode()).modal("hide")
    }

    def mounted(props: Props): Callback = Callback {

    }
    def submitForm(e: ReactEventI) = {
      e.preventDefault()
      t.modState(s => s.copy(postBiddingScreen = false))
    }

    def formClosed(state: State, props: Props): Callback = {
      // call parent handler with the new item and whether form was OK or cancelled
      println(state.postBiddingScreen)
      props.submitHandler(state.postBiddingScreen)
    }

    def render(s: State, p: Props) = {
      val headerText = "Contract - ID: 25688  Title: Videographer Needed... "
      Modal(Modal.Props(
        // header contains a cancel button (X)
        header = hide => <.span(<.button(^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.div(DashBoardCSS.Style.modalHeaderText)(headerText)),
        // this is called after the modal has been hidden (animation is completed)
        closed = () => formClosed(s, p)),
        <.form(^.onSubmit ==> submitForm)(
          <.div(^.className := "row", DashBoardCSS.Style.MarginLeftchkproduct)(
            <.div(^.className := "row")(
              <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                <.div()("Stage:")
              ),
              <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                // ToDo: the current status, e.g. Negotiating, should be in bold and come from the underlying model for the Contract.
                <.div()(<.a(^.fontWeight.bold)("Initiating "), " > ", <.a()("Ecrow "), " > ", <.a()("In Progress "), " > ", <.a()("Acceptance "), " > ", <.a()("Feedback "))
              )
            ),
            // ToDo:  these are temporary sections that really should be tabs within this modal and styles are tempoary.
            // Initiating details
            <.div(^.id := "initiatingDetail", ^.borderStyle.solid)(
              <.span(^.fontWeight.bold)("Initiating"),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                  <.div()("Project:")
                ),
                <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                  <.div()(<.a()("25688"), " Videographer Needed ...")
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                  <.div()("Employer:")
                ),
                <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                  <.div()(<.a()("Pam")), "snapshot"
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                  <.div()("Talent:")
                ),
                <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                  <.div()(<.a()("Abed")), "Choose your profile:", "picklist... ", "snapshot"
                )
              ),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(
                  <.div()("Referred By:")
                ),
                <.div(^.className := "col-md-10 col-sm-10 col-xs-10")(
                  <.div()(<.a()("Britta"))
                )
              ),
              <.div(DashBoardCSS.Style.splitContainer)(
                <.div(^.className := "split")(
                  <.div(^.className := "row")(
                    <.div(^.className := "col-md-12 col-sm-12 col-xs-12", ^.id := "dashboardResults2", BiddingScreenCSS.Style.BiddingScreenResults)(
                      <.div(^.id := "rsltScrollContainer")(
                        <.div(^.className := "container-fluid")(
                          <.div()(
                            <.div(^.className := "row", BiddingScreenCSS.Style.borderBottomHeader, BiddingScreenCSS.Style.marginLeftRight)(
                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5", DashBoardCSS.Style.slctHeaders)("Term"),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1", DashBoardCSS.Style.slctHeaders)("Employer Agreement"),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1", DashBoardCSS.Style.slctHeaders)("Talent Agreement"),
                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5", DashBoardCSS.Style.slctHeaders)("History")
                            ),
                            <.div(BiddingScreenCSS.Style.biddingScreenData)(
                              <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight)(
                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5")(
                                  <.div(/*DashBoardCSS.Style.slctHeaders*/)("Contract Template"),
                                  <.div(^.className := "row")(
                                    <.div(^.className := "col-md-12 col-sm-12 col-xs-12", BiddingScreenCSS.Style.slctBiddingInputWidth)(
                                      <.div(^.className := "btn-group")(
                                        <.button(ProjectCSS.Style.projectdropdownbtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Nolo Service..-23")(
                                          <.span(^.className := "caret")
                                        ),
                                        <.ul(^.className := "dropdown-menu")(
                                          <.li()(<.a(^.href := "#")("Item 1")),
                                          <.li()(<.a(^.href := "#")("Item 2")),
                                          <.li()(<.a(^.href := "#")("Item 3"))
                                        )
                                      )
                                    ),
                                    <.div(BiddingScreenCSS.Style.slctBiddingInputLeftContainerMargin, DashBoardCSS.Style.marginTop10px)(
                                      //<.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                                      <.div()(<.a("view"))
                                    )
                                  )
                                ),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5")("Original")
                              ),

                              <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight)(
                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5")(
                                  <.div(/*DashBoardCSS.Style.slctHeaders*/)("Rate"),
                                  <.div(^.className := "row")(
                                    <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                                      <.div()(<.input(^.className := "form-control", ^.placeholder := "25.30 USD"))
                                    )
                                  )
                                ),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5")("Original")
                              ),
                              <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight)(
                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5")(
                                  <.div()("Statement of Work"),
                                  <.div(^.className := "row")(
                                    <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                                      <.div()(
                                        <.a()("View / Modify "),
                                        "Updated: 2016-01-12 SHA256:d14a sf"
                                      )
                                    )
                                  )
                                ),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5")("Last action: Abed updated 2016-01-12")
                              ),
                              <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight)(

                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5")(
                                  <.div(/*DashBoardCSS.Style.slctHeaders*/)("Moderator:"),
                                  <.div(^.className := "row")(

                                    <.div(MessagesCSS.Style.slctMessagesInputLeftContainerMargin, DashBoardCSS.Style.marginTop10px)(
                                      //<.input(^.className:="form-control", DashBoardCSS.Style.inputHeightWidth)
                                      <.div(^.className := "btn-group")(
                                        <.button(ProjectCSS.Style.projectdropdownbtn, ^.className := "btn dropdown-toggle", "data-toggle".reactAttr := "dropdown")("Aaron Wu")(
                                          <.span(^.className := "caret")
                                        ),
                                        <.ul(^.className := "dropdown-menu")(
                                          <.li()(<.a(^.href := "#")("Jim P. Blesho")),
                                          <.li()(<.a(^.href := "#")("Remi Fastaou")),
                                          <.li()(<.a(^.href := "#")("Jami Corporation"))
                                        )
                                      )
                                    )
                                  )
                                ),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5")("Original")
                              ),

                              <.div(^.className := "row", BiddingScreenCSS.Style.marginLeftRight)(
                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5")(
                                  <.div(/*DashBoardCSS.Style.slctHeaders*/)("Completion Date"),
                                  <.div(^.className := "row")(
                                    <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                                      <.div()(<.input(^.className := "form-control", ^.placeholder := "2016-08-15"))
                                    )
                                  )
                                ),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                                <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                                <.div(^.className := "col-md-4 col-sm-5 col-xs-5")("Original")
                              )

                            )
                          ), //container

                          <.div()(
                            <.div(^.className := "row", BiddingScreenCSS.Style.borderBottomFooter, BiddingScreenCSS.Style.marginLeftRight)(
                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5", DashBoardCSS.Style.slctHeaders)("All Terms"),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                              <.div(^.className := "col-md-2 col-sm-1 col-xs-1")(<.input(^.`type` := "checkbox", DashBoardCSS.Style.rsltCheckboxStyle)),
                              <.div(^.className := "col-md-4 col-sm-5 col-xs-5")("Last action:Statement of work: Abed updated..")
                            )
                          ),
                          <.div(BiddingScreenCSS.Style.marginLeftRight)(
                            <.div(^.className := "row")(
                              <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                                <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(),
                                <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(),
                                <.div(^.className := "col-md-8 col-sm-8 col-xs-8")(
                                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Apply")(),

                                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Accept")(),
                                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Counter")(),
                                  <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Reject")()

                                )
                              )
                            )
                          )
                        ) //gigConversation
                      )
                    )
                  )
                )
              )
            ), // initiatingDetail
            // escrowDetail
            <.div(^.id := "escrowDetail", ^.borderStyle.solid)(
              <.span(^.fontWeight.bold)("Escrow"),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                  <.div()(
                    "All parties in the contract as of 2016-07-30 have agreed to the terms. Now, funding into escrow is required:",
                    <.br(),
                    "From Employer, Pam:  1 XBT requested.  Not yet funded.",
                    <.br(),
                    "Deposit bitcoin in the following contract:",
                    <.br(),
                    <.img()(^.src := "./assets/images/example-bitcoin-qr-code.png"),
                    <.br(),
                    "342ftSRCvFHfCeFFBuz4xwbeqnDw6BGUey",
                    "From Talent, Abed:  0.02 XBT  requested.",
                    <.br(),
                    "Escrow deposit, payment, and refund details <link>..."
                  )
                )
              )
            ),
            // escrowDepositDetail
            <.div(^.id := "escrowDepositDetails", ^.borderStyle.solid)(
              <.span(^.fontWeight.bold)("Escrow Deposit and Payout Details"),
              <.div(^.className := "row")(

                <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                  "The following amounts are expected deposit and payout amounts under various circumstances, based on current LivelyGig policies applicable in this situation. See ",
                  <.a(^.href := "#")("details"),
                  ".",
                  // ToDo: convert this to html.  Currency amounts should be unit-separator-aligned (decimal "." in US locale), so that not all amounts need to show lots of 000s after the decimal, but they can be visually added. See http://stackoverflow.com/questions/1363239/aligning-decimal-points-in-html
                  <.div()(<.img()(^.src := "./assets/images/escrow_payout_example.png")
                  )
                )
              )
            ),
            // inProgressDeatil
            <.div(^.id := "inProgressDetail", ^.borderStyle.solid)(
              <.span(^.fontWeight.bold)("In Progress"),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                  <.div()(
                    "Milestones:",
                    <.br(),
                    "name, deliverable status(on-track, delayed, completion status (employer, talent))",
                    <.br(),
                    "Messages: date added, subject, to, from, hyperlink",
                    <.br(),
                    "Links to deliverables: date added, name, hyperlink"
                  )
                )
              )
            ),
            // acceptanceDetail
            <.div(^.id := "acceptanceDetail", ^.borderStyle.solid)(
              <.span(^.fontWeight.bold)("Acceptance"),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                  <.div()(
                    "Final acceptance",
                    <.br(),
                    "Employer  --  accept, dispute (with confirmation)",
                    <.br(),
                    "Talent -- accept, dispute (with confirmation)",
                    <.br(),
                    "Final payout information and transaction."
                  )
                )
              )
            ),
            // feedbackDetail
            <.div(^.id := "feedbackDetail", ^.borderStyle.solid)(
              <.span(^.fontWeight.bold)("Feedback"),
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                  <.div()(
                    "From Employer to Talent, From Employer to Moderator, From Talent to Employer, From Talent to Moderator, From Moderator to Talent, From Moderator to Employer",
                    <.br(),
                    "Details of feedback (e.g. for Talent)",
                    <.br(),
                    "- communication   (1-10), - meeting schedule (1-10), - quality of deliverables (1-10), - competencies: -- skill 1, --- skill 2",
                    <.br(),
                    "For moderator: impartiality, domain competency"
                  )
                )
              )
            ),
            // shared actions bellow all of the workflow sections
            <.div(BiddingScreenCSS.Style.marginLeftRight)(
              <.div(^.className := "row")(
                <.div(^.className := "col-md-12 col-sm-12 col-xs-12")(
                  <.div(^.className := "col-md-1 col-sm-1 col-xs-1")(),
                  <.div(^.className := "col-md-2 col-sm-2 col-xs-2")(),
                  <.div(^.className := "col-md-8 col-sm-8 col-xs-8")(
                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Message")(),
                    <.button(BiddingScreenCSS.Style.createBiddingBtn, ^.className := "btn")("Close")()
                  )
                )
              )
            )
          ),
          <.div()(
            <.div(DashBoardCSS.Style.modalHeaderPadding, DashBoardCSS.Style.footTextAlign)(
              //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Post"),
              //              <.button(^.tpe := "button",^.className:="btn btn-default", DashBoardCSS.Style.marginLeftCloseBtn, ^.onClick --> hide,"Cancel")
            )
          ),
          <.div(bss.modal.footer, DashBoardCSS.Style.marginTop10px, DashBoardCSS.Style.marginLeftRight)()
        )
      )
    }
  }

  private val component = ReactComponentB[Props]("BiddingScreenModal")
    .initialState_P(p => State())
    .renderBackend[Backend]
      .componentDidUpdate(scope => Callback {
         if(scope.currentState.postBiddingScreen){
           scope.$.backend.hidemodal
         }
    })
    .build

  def apply(props: Props) = component(props)
}
