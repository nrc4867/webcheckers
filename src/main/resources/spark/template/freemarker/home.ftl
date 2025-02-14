<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl">

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl">

    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->
    <div class="playerlist">
      <h2>Players Online</h2>
      <#list users>
        <ul>
          <form action="./game" method="post">
          <#items as user>
            <li><input type="submit" name="challenge" value="${user}"></li>
          </#items>
          </form>
        </ul>
      <#else>
        <ul>
          <li>No players are online.</li>
        </ul>
      </#list>
    </div>
    <div class="playerlist">
      <h2>Spectate</h2>
      <#list ingame>
        <ul>
            <form action="./game" method="post">
          <#items as user>
              <li><input type="submit" name="challenge" value="${user}"></li>
          </#items>
            </form>
        </ul>
      <#else>
        <ul>
            <li>No players are playing checkers.</li>
        </ul>
      </#list>
    </div>
      <div class="replayList">
          <h2>Replay</h2>
      <#list pastgames>
        <ul>
            <form action="./replay" method="post">
              <#items as game>
                  <li><input type="submit" name="replay" value="${game}"></li>
              </#items>
            </form>
        </ul>
      <#else>
        <ul>
            <li>No games are available for replay.</li>
        </ul>
      </#list>
      </div>
</div>
</body>

</html>
