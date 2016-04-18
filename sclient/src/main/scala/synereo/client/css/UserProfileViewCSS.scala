package synereo.client.css

import scalacss.Defaults._

/**
  * Created by Mandar on 3/28/2016.
  */
object UserProfileViewCSS {

  object Style extends StyleSheet.Inline {

    import dsl._

    val userProfileHeadingContainerDiv = style(
      minHeight(500.px),
      display.block,
      marginLeft.auto,
      marginRight.auto
    )
    val heading = style(
      color.white,
      textAlign.center,
      marginTop(10.%%),
      fontSize(3.em)
    )

  }

}
