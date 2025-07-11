<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="Models.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Friends - QuizRizz</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="Styles/friends.css">
</head>
<body>
<div class="container">
    <div class="friends-container">
        <h2 class="mb-4 text-white">
            <i class="fas fa-user-friends"></i>
            <c:choose>
                <c:when test="${not empty profileUser}">
                    ${profileUser.username}'s Profile
                </c:when>
                <c:otherwise>
                    Friends & Users
                </c:otherwise>
            </c:choose>
        </h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>

        <!-- PROFILE -->
        <c:if test="${not empty profileUser}">
            <div class="section">
                <div class="profile-header">
                    <h3>${profileUser.username}</h3>
                    <p>Member since: ${profileUser.timeCreated != null ? profileUser.timeCreated.toString().substring(0,10) : "N/A"}</p>
                    <c:if test="${!isOwnProfile && !areFriends}">
                        <form method="post" action="friends" class="mt-3">
                            <input type="hidden" name="action" value="send">
                            <input type="hidden" name="targetUserId" value="${profileUser.id}">
                            <button type="submit" class="btn btn-light">Add Friend</button>
                        </form>
                    </c:if>
                    <c:if test="${areFriends}">
                        <span class="badge bg-success fs-6 mt-2">✓ Friends</span>
                    </c:if>
                </div>

                <div class="row mt-3">
                    <div class="col-md-3"><div class="stat-box"><h4>${profileUser.numQuizzesCreated}</h4><small>Created</small></div></div>
                    <div class="col-md-3"><div class="stat-box"><h4>${profileUser.numQuizzesTaken}</h4><small>Taken</small></div></div>
                    <div class="col-md-3"><div class="stat-box"><h4>${profileUser.wasTop1 ? "Yes" : "No"}</h4><small>Top Achiever</small></div></div>
                    <div class="col-md-3"><div class="stat-box"><h4>${profileUser.takenPractice ? "Yes" : "No"}</h4><small>Practice Mode</small></div></div>
                </div>

                <c:if test="${not empty userQuizzes}">
                    <h5 class="mt-4">Quizzes by ${profileUser.username}</h5>
                    <div class="row">
                        <c:forEach var="quiz" items="${userQuizzes}">
                            <div class="col-md-6 mb-2">
                                <div class="card">
                                    <div class="card-body">
                                        <h6>${quiz.quizTitle}</h6>
                                        <p class="card-text small">${quiz.description}</p>
                                        <small class="text-muted">Taken ${quiz.submissionsNumber} times</small><br>
                                        <a href="quiz-summary.jsp?quizId=${quiz.quizId}" class="btn btn-sm btn-primary mt-1">View Quiz</a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>

                <div class="text-center mt-3">
                    <a href="friends" class="btn btn-secondary">Back to Friends</a>
                </div>
            </div>
        </c:if>

        <!-- MAIN INTERFACE -->
        <c:if test="${empty profileUser}">
            <!-- PENDING FRIEND REQUESTS SECTION -->
            <c:if test="${not empty pendingRequests}">
                <div class="section">
                    <h4><i class="fas fa-user-clock"></i> Pending Friend Requests</h4>
                    <c:forEach var="request" items="${pendingRequests}">
                        <div class="pending-request">
                            <h6><i class="fas fa-user-plus"></i> Friend Request from
                                <c:choose>
                                    <c:when test="${not empty request.senderName}">
                                        <strong>${request.senderName}</strong>
                                    </c:when>
                                    <c:otherwise>
                                        User ID: ${request.senderId}
                                    </c:otherwise>
                                </c:choose>
                            </h6>
                            <small class="text-muted">Sent on: ${request.sentAt.toString().substring(0,19)}</small>
                            <div class="btn-group mt-2">
                                <form method="post" action="friends" style="display:inline;">
                                    <input type="hidden" name="action" value="accept">
                                    <input type="hidden" name="requestId" value="${request.requestId}">
                                    <button type="submit" class="btn btn-success btn-sm">
                                        <i class="fas fa-check"></i> Accept
                                    </button>
                                </form>
                                <form method="post" action="friends" style="display:inline;">
                                    <input type="hidden" name="action" value="reject">
                                    <input type="hidden" name="requestId" value="${request.requestId}">
                                    <button type="submit" class="btn btn-danger btn-sm">
                                        <i class="fas fa-times"></i> Reject
                                    </button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <div class="section">
                <h4><i class="fas fa-search"></i> Find Users</h4>
                <form method="get" action="friends">
                    <div class="input-group">
                        <input type="text" name="search" class="form-control" placeholder="Search..." value="${searchQuery}">
                        <button class="btn btn-primary" type="submit">Search</button>
                    </div>
                </form>

                <c:if test="${not empty searchResults}">
                    <h6 class="mt-3">Results</h6>
                    <div class="row">
                        <c:forEach var="user" items="${searchResults}">
                            <div class="col-md-6 mb-2">
                                <div class="card">
                                    <div class="card-body">
                                        <h6>${user.username}</h6>
                                        <small class="text-muted">${user.numQuizzesCreated} created, ${user.numQuizzesTaken} taken</small>
                                        <div class="mt-2">
                                            <a href="friends?action=profile&userId=${user.id}" class="btn btn-sm btn-outline-primary">View Profile</a>
                                            <form method="post" action="friends" style="display:inline;">
                                                <input type="hidden" name="action" value="send">
                                                <input type="hidden" name="targetUserId" value="${user.id}">
                                                <button class="btn btn-sm btn-success">Add Friend</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${not empty searchQuery and empty searchResults}">
                    <div class="empty-state">
                        <i class="fas fa-user-slash"></i>
                        <p>No users found matching "${searchQuery}"</p>
                    </div>
                </c:if>
            </div>

            <div class="section">
                <h4><i class="fas fa-user-friends"></i> Your Friends</h4>
                <c:if test="${not empty friends}">
                    <div class="row">
                        <c:forEach var="friend" items="${friends}">
                            <div class="col-md-6 mb-2">
                                <div class="card">
                                    <div class="card-body">
                                        <h6>${friend.username}</h6>
                                        <small class="text-muted">${friend.numQuizzesCreated} created, ${friend.numQuizzesTaken} taken</small>
                                        <div class="mt-2">
                                            <a href="friends?action=profile&userId=${friend.id}" class="btn btn-sm btn-outline-primary">View Profile</a>
                                            <a href="message?to=${friend.id}" class="btn btn-success btn-sm ms-1">
                                                <i class="fas fa-paper-plane"></i> Message
                                            </a>
                                            <button type="button" class="btn btn-outline-danger btn-sm ms-1" onclick="removeFriend(${friend.id}, '${friend.username}')">Remove</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
                <c:if test="${empty friends}">
                    <div class="empty-state">
                        <i class="fas fa-user-friends"></i>
                        <p>You don't have any friends yet. Search above!</p>
                    </div>
                </c:if>
                <div class="text-center mt-3">
                    <a href="homepage.jsp" class="btn btn-secondary">Back to Home</a>
                </div>
            </div>
        </c:if>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="removeFriendModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Remove Friend</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p>Remove <span id="friendName"></span> from your friends list?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <form method="post" action="friends" style="display:inline;">
                    <input type="hidden" name="action" value="remove">
                    <input type="hidden" name="targetUserId" id="friendIdToRemove">
                    <button type="submit" class="btn btn-danger">Remove</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function removeFriend(friendId, friendName) {
        document.getElementById('friendName').textContent = friendName;
        document.getElementById('friendIdToRemove').value = friendId;
        new bootstrap.Modal(document.getElementById('removeFriendModal')).show();
    }
    document.addEventListener('DOMContentLoaded', function() {
        const cards = document.querySelectorAll('.card');
        cards.forEach((card, index) => {
            card.style.opacity = '0';
            card.style.transform = 'translateY(20px)';
            setTimeout(() => {
                card.style.transition = 'all 0.5s ease';
                card.style.opacity = '1';
                card.style.transform = 'translateY(0)';
            }, index * 100);
        });
    });
</script>
</body>
</html>
