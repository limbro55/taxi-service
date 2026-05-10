
POST("http://localhost:8080/drivers") {
    header("Content-Type", "application/json")
    body(
        """
        {
          "name": "Limbro",
          "email": "limbro@taxi.com",
          "phone": "+79991112233",
          "carModel": "Tesla",
          "carNumber": "A777AA"
        }
        """.trimIndent()
    )

}
