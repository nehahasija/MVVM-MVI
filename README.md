User story description
As a user, one is able to see a list of all the rocket launch sites.
Each item on the list  show:
● the launch site name (e.g. “CCAFS SLC 40”)
● the mission name: (e.g. “Starlink-15 (v1.0)”)
● a thumbnail of the mission patch (e.g.
https://images2.imgbox.com/9a/96/nLppz9HW_o.png)
Tapping on an item of the list one can  see, on a new screen, the details of the selected
launch:
● a big image of the mission patch (e.g.
https://images2.imgbox.com/9a/96/nLppz9HW_o.png)
● the mission name: (e.g. “Starlink-15 (v1.0)”)
● the rocket name and the rocket type: (e.g. “Falcon 9 FT”)
● the launch site name (e.g. “CCAFS SLC 40”)
As a Tester, one can  see the GraphQL server URL used for the query inside the Debug
screen.
Technical requirements
1. App follows architecture-pattern (Compose , MVVM+MVI, Navigation between screen via navHostController)
2. The GraphQL server URL is displayed inside the debug screen.
9. The GraphQL Schema is available inside the module “core-rocketserver”
10. (documentation available here, click on the “DOCS” tab on the right side of the page:
https://apollo-fullstack-tutorial.herokuapp.com/)
11. The app,  supports both landscape and portrait mode
